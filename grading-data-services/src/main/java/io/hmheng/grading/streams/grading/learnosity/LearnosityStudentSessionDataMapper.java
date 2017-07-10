package io.hmheng.grading.streams.grading.learnosity;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.hmheng.grading.learnosity.domain.StudentSession;
import java.io.IOException;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by nandipatim on 3/8/17.
 */
@Slf4j
@Component
public class LearnosityStudentSessionDataMapper implements Function<byte[], StudentSession> {

  private ObjectReader objectReader;

  public LearnosityStudentSessionDataMapper() {
    objectReader = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .readerFor(StudentSession.class);
  }

  @Override
  public StudentSession apply(byte[] bytes) {
    try {
      log.info("Deserializing message into String from Lernosity Session Data services");
      StudentSession studentSession = (StudentSession) objectReader.readValue(bytes);
      log.info("LernosityStudentSessionDataMapper in Lernosity DataMapper {} ",studentSession);
      return studentSession;
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
