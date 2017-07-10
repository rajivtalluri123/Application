package io.hmheng.grading.streams.kinesis;

import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import java.io.Serializable;

/**
 * Created by nandipatim on 6/21/17.
 */
public class KinesisStreamProducer extends KinesisProducer implements Serializable{

  public KinesisStreamProducer(KinesisProducerConfiguration configuration){
    super(configuration);
  }
}
