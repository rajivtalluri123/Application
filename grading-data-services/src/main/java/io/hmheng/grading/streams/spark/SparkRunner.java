package io.hmheng.grading.streams.spark;

import io.hmheng.grading.streams.InputStreamFactory;
import io.hmheng.grading.streams.StreamingContextFactory;
import io.hmheng.grading.streams.kinesis.model.KinesisMessage;
import io.hmheng.springframework.extn.context.WorkerSpringApplicationContext;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by nandipatim on 5/17/17.
 */
@Slf4j
@Component
public class SparkRunner implements Serializable{

  private static final long serialVersionUID = 1L;

  private final StreamingContextFactory streamingContextFactory;

  private final InputStreamFactory<KinesisMessage> inputStreamFactory;

  private final String[] activeProfiles;


  @Autowired
  public SparkRunner(StreamingContextFactory streamingContextFactory,InputStreamFactory<KinesisMessage> inputStreamFactory
      ,Environment env) {
    this.streamingContextFactory = streamingContextFactory;
    this.inputStreamFactory = inputStreamFactory;
    this.activeProfiles = env.getActiveProfiles();
  }

  public void execute() throws InterruptedException {

    log.info("Active profiles {} ", Arrays.toString(activeProfiles));

    JavaStreamingContext streamingContext = streamingContextFactory.create();
    //streamingContext.checkpoint(streamingContext.sparkContext().getConf().get(Constants.CHECKPOINT_DIR_KEY));
    Map<String ,JavaDStream<KinesisMessage>> eventsStreamMap = inputStreamFactory.create(streamingContext);

    Set<String> eventSet= eventsStreamMap.keySet();

    for(String streamSession:eventSet){
      JavaDStream<KinesisMessage> kinesisMessageJavaDStream = eventsStreamMap.get(streamSession);
      if (kinesisMessageJavaDStream != null) {

        kinesisMessageJavaDStream.foreachRDD((rdd, time) -> {

          rdd.foreachPartition(part -> {
            // Create connections / long lived expensive objects objects here.
            // This code already runs on the worker and is kept "alive" between batches, so Spring initialization only
            // takes place once
            if (part != null) {

              part.forEachRemaining(row -> {
                try {
                  log.info("Processing stream {}, message {}", row.getStream(),
                      row.getOriginalMessageWithoutPayload().getSequenceNumber());
                  String data = new String(row.getOriginalMessageWithoutPayload().getData().array());

                  if (!isAbilityEstimate(data)) {

                    ApplicationContext applicationContext = WorkerSpringApplicationContext.getContext(activeProfiles);
                    Function<byte[], Object> mapperBean = (Function<byte[], Object>) (applicationContext != null ? applicationContext.getBean(row.getMapperBeanClass()) : null);

                    Consumer<Object> processorBean = (Consumer<Object>) applicationContext.getBean(row.getProcessorBeanClass());

                    Object deserializedBean = mapperBean.apply(row.getMessagePayload());

                    log.info("Processed object from stream {}, Sequence Number {} , message {}: ", row.getStream(),
                        row.getOriginalMessageWithoutPayload().getSequenceNumber(), data);

                    processorBean.accept(deserializedBean);
                  }
                } catch (Throwable t) {
                  log.error("Received an error, forwarding to dead letter processor.", t);
                  //TODO Use Dead Letter to push it to Dead Letter Queue
                }
              });
            }
          });
        });
      }
    }
    run(streamingContext);

  }

  private Boolean isAbilityEstimate(String learnosityData){

    if(learnosityData == null)
      return Boolean.FALSE;

    return (learnosityData.contains("\"ability_estimate\"") || learnosityData.contains("ability_estimate"));
  }

  protected void run(JavaStreamingContext streamingContext) throws InterruptedException {
    streamingContext.start();
    streamingContext.awaitTermination();

  }

}
