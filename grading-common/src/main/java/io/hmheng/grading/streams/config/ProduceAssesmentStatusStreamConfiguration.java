package io.hmheng.grading.streams.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by nandipatim on 5/18/17.
 */
@Data
@Component
@NoArgsConstructor
@ConfigurationProperties("aws.kinesis.produce.assesmentstatus")
@EnableConfigurationProperties
public class ProduceAssesmentStatusStreamConfiguration extends StreamConfiguration {

  @Override
  public ConfigType getConfigType(){

    return ConfigType.PRODUCER;
  }
}
