package io.hmheng.grading.streams.kinesis;

import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.amazonaws.services.kinesis.producer.Attempt;
import com.amazonaws.services.kinesis.producer.UserRecordFailedException;
import com.amazonaws.services.kinesis.producer.UserRecordResult;
import com.google.common.collect.Iterables;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.hmheng.grading.streams.config.StreamConfiguration;
import io.hmheng.grading.streams.kinesis.model.KinesisMessage;
import io.hmheng.grading.streams.kinesis.model.KinesisPutRecordResult;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.springframework.stereotype.Component;

/**
 * Created by nandipatim on 6/20/17.
 */
//@Component
@Slf4j
public class KPLDataStreamGenerator  implements SparkDataKinesisGenerator<KinesisMessage> {

  private static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(1);

  final AtomicLong sequenceNumber = new AtomicLong(0);

  final AtomicLong completed = new AtomicLong(0);

  private static final Random RANDOM = new Random();

  private static final int RECORDS_PER_SECOND = 2000;

  private static final int SECONDS_TO_RUN = 5;

  @Override
  public Map<String, Map<KinesisMessage,PutRecordResult>> sendData(StreamConfiguration streamConfiguration, JavaDStream<KinesisMessage> data){

    KinesisStreamProducer producer = new KinesisStreamProducer(streamConfiguration.getKinesisProducerConfiguration());

    String steamName = streamConfiguration.getStream();

    Map<String , Map<KinesisMessage,PutRecordResult>> returnResult = new HashMap<>();
    returnResult.put(steamName, new HashMap<>());


    final FutureCallback<UserRecordResult> callback = new FutureCallback<UserRecordResult>() {
      @Override
      public void onSuccess(UserRecordResult userRecordResult) {
        completed.getAndIncrement();
      }

      @Override
      public void onFailure(Throwable t) {
        // We don't expect any failures during this sample. If it
        // happens, we will log the first one and exit.
        if (t instanceof UserRecordFailedException) {
          Attempt last = Iterables.getLast(
              ((UserRecordFailedException) t).getResult().getAttempts());
          log.error(String.format(
              "Record failed to put - %s : %s",
              last.getErrorCode(), last.getErrorMessage()));
        }
        log.error("Exception during put", t);
        System.exit(1);
      }
    };


    // The lines within run() are the essence of the KPL API.
    final Runnable putOneRecord = new Runnable() {
      @Override
      public void run() {

        data.foreachRDD((rdd, time) -> {
          rdd.foreachPartition(partition -> {
            partition.forEachRemaining(row -> {
              String randomKey = UUID.randomUUID().toString();
              String explicitHashKey = randomExplicitHashKey();
              log.info("Before Stream data for Partition Key {} , Random Explicit Hashkey {}",randomKey , explicitHashKey);
              ListenableFuture<UserRecordResult> f =
                  producer.addUserRecord(steamName, randomKey, explicitHashKey , row.getOriginalMessageWithoutPayload().getData());
              Futures.addCallback(f, callback);
              if(f.isDone()){
                try {
                  log.info("Processed for Partition Key {} , Random Explicit Hashkey {}",randomKey , explicitHashKey);

                  UserRecordResult recordResult = f.get();
                  Map<KinesisMessage,PutRecordResult> putRecordRequestMap =  returnResult.get(steamName);
                  PutRecordResult putRecordResult = new KinesisPutRecordResult()
                      .withAttempts(recordResult.getAttempts())
                      .withIsSucessful(recordResult.isSuccessful())
                      .withSequenceNumber(recordResult.getSequenceNumber())
                      .withShardId(recordResult.getShardId());
                  log.info("User Record Result for Partition Key {} , Random Explicit Hashkey {} , Sequence Number {} , Shard Id {}"
                      ,randomKey , explicitHashKey , recordResult.getSequenceNumber(), recordResult.getShardId());

                  putRecordRequestMap.put(row , putRecordResult);

                } catch (InterruptedException e) {
                  log.error(String.format(
                      "Record failed to put - %s : %s",
                      e.getCause(), e.getMessage()));
                } catch (ExecutionException e) {
                  log.error(String.format(
                      "Record failed to put - %s : %s",
                      e.getCause(), e.getMessage()));
                }
              }
            });
          });
        });
      }
    };


    // This gives us progress updates
    EXECUTOR.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        long put = sequenceNumber.get();
        long total = RECORDS_PER_SECOND * SECONDS_TO_RUN;
        double putPercent = 100.0 * put / total;
        long done = completed.get();
        double donePercent = 100.0 * done / total;
        log.info(String.format(
            "Put %d of %d so far (%.2f %%), %d have completed (%.2f %%)",
            put, total, putPercent, done, donePercent));
      }
    }, 1, 1, TimeUnit.SECONDS);

    // Kick off the puts
    log.info(String.format(
        "Starting puts... will run for %d seconds at %d records per second",
        SECONDS_TO_RUN, RECORDS_PER_SECOND));
    executeAtTargetRate(EXECUTOR, putOneRecord, sequenceNumber, SECONDS_TO_RUN, RECORDS_PER_SECOND);

    // Wait for puts to finish. After this statement returns, we have
    // finished all calls to putRecord, but the records may still be
    // in-flight. We will additionally wait for all records to actually
    // finish later.
    try {
      EXECUTOR.awaitTermination(SECONDS_TO_RUN + 1, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      log.error(String.format(
          "Record failed to put - %s : %s",
          e.getCause(), e.getMessage()));
    }

    // If you need to shutdown your application, call flushSync() first to
    // send any buffered records. This method will block until all records
    // have finished (either success or fail). There are also asynchronous
    // flush methods available.
    //
    // Records are also automatically flushed by the KPL after a while based
    // on the time limit set with Configuration.setRecordMaxBufferedTime()
    log.info("Waiting for remaining puts to finish...");
    producer.flushSync();
    log.info("All records complete.");

    // This kills the child process and shuts down the threads managing it.
    producer.destroy();
    log.info("Finished.");

    return returnResult;
  }

  public static String randomExplicitHashKey() {
    return new BigInteger(128, RANDOM).toString(10);
  }

  /**
     * Executes a function N times per second for M seconds with a
     * ScheduledExecutorService. The executor is shutdown at the end. This is
     * more precise than simply using scheduleAtFixedRate.
     *
     * @param exec
     *            Executor
     * @param task
     *            Task to perform
     * @param counter
     *            Counter used to track how many times the task has been
     *            executed
     * @param durationSeconds
     *            How many seconds to run for
     * @param ratePerSecond
     *            How many times to execute task per second
     */
    private static void executeAtTargetRate(
    final ScheduledExecutorService exec,
    final Runnable task,
    final AtomicLong counter,
    final int durationSeconds,
    final int ratePerSecond) {
      exec.scheduleWithFixedDelay(new Runnable() {
        final long startTime = System.nanoTime();

        @Override
        public void run() {
          double secondsRun = (System.nanoTime() - startTime) / 1e9;
          double targetCount = Math.min(durationSeconds, secondsRun) * ratePerSecond;

          while (counter.get() < targetCount) {
            counter.getAndIncrement();
            try {
              task.run();
            } catch (Exception e) {
              log.error("Error running task", e);
              System.exit(1);
            }
          }

          if (secondsRun >= durationSeconds) {
            exec.shutdown();
          }
        }
      }, 0, 1, TimeUnit.MILLISECONDS);
    }


  @Override
  public Map<String, KinesisPutRecordResult> pushKinesisMessage(StreamConfiguration streamConfiguration, ByteBuffer message) {
    return null;
  }
}
