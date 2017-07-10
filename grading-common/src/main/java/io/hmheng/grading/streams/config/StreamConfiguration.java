package io.hmheng.grading.streams.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.DescribeStreamResult;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by nandipatim on 5/17/17.
 */
@Data
@NoArgsConstructor
public class StreamConfiguration implements Serializable{

  @NonNull
  private String appName;

  @NonNull
  private String stream;

  @NonNull
  private String region;

  @NonNull
  private String kinesisEndpoint;

  private boolean useSts;

  private String roleArn;

  private String roleSessionName;

  private Integer sparkProcessingParallelism;

  public AmazonKinesis kinesisClient() {
    AmazonKinesisClientBuilder builder = AmazonKinesisClientBuilder.standard().withRegion(region);
    if (useSts) {
      builder = builder.withCredentials(new STSAssumeRoleSessionCredentialsProvider.Builder(roleArn, roleSessionName)
          .withStsClient(AWSSecurityTokenServiceClientBuilder.standard().withCredentials(new
              DefaultAWSCredentialsProviderChain()).withRegion(region).build()).build());
    }

    return builder.build();
  }

  public DescribeStreamResult describeStream() {
    return kinesisClient().describeStream(getStream());
  }

  public ConfigType getConfigType(){
    return ConfigType.CONSUMER;
  }

  public AWSCredentialsProvider getAWSCredentialsProvider(){

    if (useSts) {
      return new STSAssumeRoleSessionCredentialsProvider.Builder(roleArn, roleSessionName)
          .withStsClient(AWSSecurityTokenServiceClientBuilder.standard().withCredentials(new
          DefaultAWSCredentialsProviderChain()).withRegion(region).build()).build();
    }

    return new DefaultAWSCredentialsProviderChain();
  }

  public KinesisProducerConfiguration getKinesisProducerConfiguration(){

    KinesisProducerConfiguration config = new KinesisProducerConfiguration();
    config.setRegion(region);
    config.setCredentialsProvider(getAWSCredentialsProvider());
    config.setMaxConnections(1);
    config.setRequestTimeout(60000);
    config.setRecordMaxBufferedTime(15000);

    return config;
  }
}
