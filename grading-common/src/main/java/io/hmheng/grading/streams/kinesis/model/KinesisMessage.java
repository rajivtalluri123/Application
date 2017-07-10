package io.hmheng.grading.streams.kinesis.model;

import com.amazonaws.services.kinesis.model.Record;
import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KinesisMessage<T> implements Serializable {

  private String appName;

  private String stream;

  private byte[] messagePayload;

  private Class<? extends Function<byte[], T>> mapperBeanClass;

  private Class<? extends Consumer<T>> processorBeanClass;

  private Record originalMessageWithoutPayload;
}
