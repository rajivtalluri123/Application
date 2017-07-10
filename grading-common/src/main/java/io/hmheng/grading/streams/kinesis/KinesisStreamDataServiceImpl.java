package io.hmheng.grading.streams.kinesis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.hmheng.grading.learnosity.domain.StudentSession;
import io.hmheng.grading.streams.config.StreamConfiguration;
import io.hmheng.grading.streams.kinesis.model.KinesisPutRecordResult;
import java.nio.ByteBuffer;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nandipatim on 6/26/17.
 */
@Service
@Slf4j
public class KinesisStreamDataServiceImpl implements KinesisStreamDataService {

  @Autowired
  private SimpleKinesisDataStreamGenerator simpleKinesisDataStreamGenerator;

  @Override
  public KinesisPutRecordResult pushToStream(Object streamObject , StreamConfiguration streamConfiguration){

    ObjectMapper objectMapper = new ObjectMapper();
    String json = null;
    try {
      json = objectMapper.writeValueAsString(streamObject);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    log.info("String pushed to Stream {}  , Data Pushed is  {}",streamConfiguration.getStream() , json);

    byte[] studentSessionBytes = json.getBytes();

    ByteBuffer byteBuffer = ByteBuffer.wrap(studentSessionBytes);

    Map<String , KinesisPutRecordResult> result = simpleKinesisDataStreamGenerator.pushKinesisMessage(streamConfiguration , byteBuffer);

    return result.get(streamConfiguration.getStream());
  }
}
