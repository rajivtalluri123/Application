package io.hmheng.grading.streams.kinesis;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import io.hmheng.grading.streams.config.StreamConfiguration;
import io.hmheng.grading.streams.kinesis.model.KinesisMessage;
import io.hmheng.grading.streams.kinesis.model.KinesisPutRecordResult;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by nandipatim on 5/18/17.
 */
@Component
@Slf4j
public class SimpleSparkDataKinesisStreamGenerator implements SparkDataKinesisGenerator<KinesisMessage> {

  @Autowired
  private SimpleKinesisDataStreamGenerator simpleKinesisDataStreamGenerator;

  @Override
  public Map<String, Map<KinesisMessage,PutRecordResult>> sendData(StreamConfiguration streamConfiguration, JavaDStream<KinesisMessage> data) {

    String steamName = streamConfiguration.getStream();

    Map<String , Map<KinesisMessage,PutRecordResult>> returnResult = new HashMap<>();
    returnResult.put(steamName, new HashMap<>());
    data.foreachRDD((rdd, time) -> {
      rdd.foreachPartition(partition -> {
        AmazonKinesis amazonKinesisClient = streamConfiguration.kinesisClient();
        partition.forEachRemaining(row -> {
          PutRecordRequest putRecordRequest = new PutRecordRequest().withStreamName(steamName)
              .withPartitionKey(UUID.randomUUID().toString())
              .withData(row.getOriginalMessageWithoutPayload().getData());
          String rawData  = new String(row.getOriginalMessageWithoutPayload().getData().array());
          log.info("Push Data to Scores :{} , message Sequence : {}", rawData,
              row.getOriginalMessageWithoutPayload().getSequenceNumber());
          PutRecordResult putRecordResult = amazonKinesisClient.putRecord(putRecordRequest);
          Map<KinesisMessage,PutRecordResult> putRecordRequestMap =  returnResult.get(steamName);
          log.info("After Push Scores Shard Id :{} , message Sequence : {}", putRecordResult.getShardId(),
              putRecordResult.getSequenceNumber());
          putRecordRequestMap.put(row , putRecordResult);
        });
      });
    });
    return returnResult;
  }



  @Override
  public Map<String, KinesisPutRecordResult> pushKinesisMessage(StreamConfiguration streamConfiguration, ByteBuffer message) {
    return simpleKinesisDataStreamGenerator.pushKinesisMessage(streamConfiguration , message);
  }
}
