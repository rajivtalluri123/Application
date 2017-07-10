package io.hmheng.grading.streams.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by nandipatim on 5/17/17.
 */
@Data
@Component
@NoArgsConstructor
@ConfigurationProperties("aws.kinesis.consume.assesmentscores")
@EnableConfigurationProperties
public class ConsumeAssesmentScoresStreamConfiguration extends StreamConfiguration {

  private String mapperBeanClass;

  private String processorBeanClass;

  @Override
  public ConfigType getConfigType(){
    return ConfigType.CONSUMER;
  }

}
