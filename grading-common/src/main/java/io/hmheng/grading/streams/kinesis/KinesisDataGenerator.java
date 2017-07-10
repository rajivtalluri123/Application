package io.hmheng.grading.streams.kinesis;

import io.hmheng.grading.streams.config.StreamConfiguration;
import io.hmheng.grading.streams.kinesis.model.KinesisPutRecordResult;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Map;

public interface KinesisDataGenerator<T> extends Serializable {

  Map<String , KinesisPutRecordResult> pushKinesisMessage(StreamConfiguration streamConfiguration, ByteBuffer message);
}
