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
import org.springframework.stereotype.Component;

/**
 * Created by nandipatim on 5/18/17.
 */
@Component
@Slf4j
public class SimpleKinesisDataStreamGenerator implements KinesisDataGenerator<KinesisMessage> {

  @Override
  public Map<String , KinesisPutRecordResult> pushKinesisMessage(StreamConfiguration streamConfiguration,
        ByteBuffer message) {

    String steamName = streamConfiguration.getStream();

    Map<String , KinesisPutRecordResult> returnResult = new HashMap<>();

    AmazonKinesis amazonKinesisClient = streamConfiguration.kinesisClient();
    PutRecordRequest putRecordRequest = new PutRecordRequest().withStreamName(steamName)
        .withPartitionKey(UUID.randomUUID().toString())
        .withData(message);
    String rawData  = new String(message.array());
    log.info("Push Data to Scores :{}", rawData);
    PutRecordResult putRecordResult = amazonKinesisClient.putRecord(putRecordRequest);
    log.info("After Push Scores Shard Id :{} , message Sequence : {}", putRecordResult.getShardId(),
        putRecordResult.getSequenceNumber());
    KinesisPutRecordResult kinesisPutRecordResult = new KinesisPutRecordResult(putRecordResult);
    returnResult.put(steamName , kinesisPutRecordResult);
    return returnResult;
  }

}
