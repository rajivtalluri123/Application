package io.hmheng.grading.streams.kinesis.model;

import com.amazonaws.services.kinesis.model.Record;
import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by fodori on 2/21/17.
 */
@Data
@Builder
@Slf4j
public class KinesisRecordMapper<T>
    implements org.apache.spark.api.java.function.Function<Record, KinesisMessage<T>>, Serializable {

  private final String stream;

  private final String appName;

  private final Class<? extends Function<byte[], T>> mapperBeanClass;

  private final Class<? extends Consumer<T>> processorBeanClass;

  @Override
  public String toString() {
    return "KinesisRecordMapper{" +
        "stream='" + stream + '\'' +
        ", appName='" + appName + '\'' +
        ", mapperBeanClass=" + mapperBeanClass +
        ", processorBeanClass=" + processorBeanClass +
        '}';
  }

  @Override
  public KinesisMessage call(Record record) throws Exception {
    if(record != null) {
      String data = new String(record.getData().array());
      log.info(" Records in KinesisRecordMapper {}", data);
    }
    KinesisMessage kinesisMessage = ((KinesisMessage.KinesisMessageBuilder<T>) KinesisMessage.builder()).stream(stream).appName(appName)
        .mapperBeanClass(mapperBeanClass).processorBeanClass(processorBeanClass)
        .messagePayload(record.getData().array()).originalMessageWithoutPayload(record).build();

    return kinesisMessage;
  }
}
