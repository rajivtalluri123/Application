package io.hmheng.grading.streams.kinesis;

import com.amazonaws.services.kinesis.model.PutRecordResult;
import io.hmheng.grading.streams.config.StreamConfiguration;
import java.util.Map;
import org.apache.spark.streaming.api.java.JavaDStream;

/**
 * Created by nandipatim on 5/18/17.
 */
public interface SparkDataKinesisGenerator<T> extends KinesisDataGenerator {

   Map<String , Map<T,PutRecordResult>> sendData(StreamConfiguration streamConfiguration, JavaDStream<T> data);
}
