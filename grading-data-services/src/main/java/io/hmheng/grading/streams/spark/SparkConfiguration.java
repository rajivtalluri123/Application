package io.hmheng.grading.streams.spark;

import io.hmheng.grading.streams.InputStreamFactory;
import io.hmheng.grading.streams.StreamingContextFactory;
import io.hmheng.grading.streams.kinesis.model.KinesisMessage;
import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by nandipatim on 1/24/17.
 */
@Configuration
@ComponentScan
@Slf4j
public class SparkConfiguration implements Serializable{

  @Autowired
  StreamingContextFactory streamingContextFactory;

  @Autowired
  InputStreamFactory<KinesisMessage> inputStreamFactory;

  @Autowired
  Environment environment;

  @Bean
  SparkRunner sparkRunner() {


      SparkRunner sparkRunner = new SparkRunner(streamingContextFactory , inputStreamFactory
          ,environment);
    try {
      sparkRunner .execute();
    } catch (InterruptedException e) {
      log.error("Error while starting Spark Runner {} ",e);
      e.printStackTrace();
    }
    return sparkRunner;
  }
}
