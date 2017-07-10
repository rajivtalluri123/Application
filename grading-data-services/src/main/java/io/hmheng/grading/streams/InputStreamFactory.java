package io.hmheng.grading.streams;

import java.util.Map;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public interface InputStreamFactory<T> {
  Map<String , JavaDStream<T>> create(JavaStreamingContext streamingContext);
}
