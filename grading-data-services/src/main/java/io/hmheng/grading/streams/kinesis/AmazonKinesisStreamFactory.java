package io.hmheng.grading.streams.kinesis;

import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import io.hmheng.grading.streams.InputStreamFactory;
import io.hmheng.grading.streams.config.ConfigType;
import io.hmheng.grading.streams.config.ConsumeAssesmentScoresStreamConfiguration;
import io.hmheng.grading.streams.config.StreamConfiguration;
import io.hmheng.grading.streams.kinesis.model.KinesisMessage;
import io.hmheng.grading.streams.kinesis.model.KinesisRecordMapper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kinesis.KinesisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by nandipatim on 1/24/17.
 */
@Slf4j
@Component
public class AmazonKinesisStreamFactory implements InputStreamFactory<KinesisMessage>, Serializable {

  private static final long serialVersionUID = 1L;

  @Autowired
  private ConsumeAssesmentScoresStreamConfiguration consumeAssesmentScoresStreamConfiguration;

  public Map<String , JavaDStream<KinesisMessage>> create(JavaStreamingContext streamingContext) {

    Map<String , JavaDStream<KinesisMessage>> dStreamMap = new HashMap<>();

    if(consumeAssesmentScoresStreamConfiguration == null){
      throw new RuntimeException("Stream Configurations cannot be Null for Consume Stream from Lernosity");
    }

    try {
        List<JavaDStream<KinesisMessage>> dStreamLists = new ArrayList<>();
        log.debug("Creating spark streams for stream {}", consumeAssesmentScoresStreamConfiguration.getStream());
        int numShards = consumeAssesmentScoresStreamConfiguration.describeStream().getStreamDescription().getShards().size();
        log.debug("Found {} shards for {}", numShards, consumeAssesmentScoresStreamConfiguration.getStream());
        dStreamLists.addAll(createStreams(streamingContext, consumeAssesmentScoresStreamConfiguration, new Duration(2000), numShards));
        JavaDStream<KinesisMessage> uStreams = unionStreams(dStreamLists, streamingContext);
        uStreams.repartition(consumeAssesmentScoresStreamConfiguration.getSparkProcessingParallelism());
        dStreamMap.put(consumeAssesmentScoresStreamConfiguration.getRoleSessionName() , uStreams);
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      throw new RuntimeException(e);
    }

    return dStreamMap;
  }

  protected List<JavaDStream<KinesisMessage>> createStreams(JavaStreamingContext jssc,
      StreamConfiguration streamConfig, Duration kinesisCheckpointInterval, int numStreams)
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    List<JavaDStream<KinesisMessage>> dStreamsList = new ArrayList<>(numStreams);
    for (int i = 0; i < numStreams; i++) {
      dStreamsList.add(createStream(jssc, streamConfig, kinesisCheckpointInterval));
    }
    return dStreamsList;
  }

  protected JavaDStream<KinesisMessage> createStream(JavaStreamingContext jssc,
      StreamConfiguration streamConfig, Duration kinesisCheckpointInterval) throws ClassNotFoundException {

    Class<? extends Function<byte[], Object>> mapperBeanClass = null;
    Class<? extends Consumer<Object>> processorBeanClass = null;
    if(ConfigType.CONSUMER == streamConfig.getConfigType()) {
      mapperBeanClass = (Class<Function<byte[], Object>>) Class.forName(
          ((ConsumeAssesmentScoresStreamConfiguration) streamConfig).getMapperBeanClass());
      processorBeanClass = (Class<Consumer<Object>>) Class.forName(
          ((ConsumeAssesmentScoresStreamConfiguration) streamConfig).getProcessorBeanClass());
    }

      KinesisRecordMapper kinesisRecordMapper = KinesisRecordMapper.builder().stream(streamConfig.getStream())
        .appName(streamConfig.getAppName())
        //TODO Only need for Consumer Streams not for Producer Streams
          .mapperBeanClass(mapperBeanClass)
          .processorBeanClass(processorBeanClass).build();

    JavaDStream<KinesisMessage> stream;
    if (streamConfig.isUseSts()) {
      stream = KinesisUtils
          .createStream(jssc, streamConfig.getAppName(), streamConfig.getStream(),  streamConfig.getKinesisEndpoint(),
              streamConfig.getRegion(), InitialPositionInStream.TRIM_HORIZON, kinesisCheckpointInterval,
              StorageLevel.MEMORY_AND_DISK_2(), kinesisRecordMapper, KinesisMessage.class, null, null,
              streamConfig.getRoleArn(), streamConfig.getRoleSessionName(), null);
    } else {
      stream = KinesisUtils
          .createStream(jssc, streamConfig.getAppName(), streamConfig.getStream(), streamConfig.getKinesisEndpoint(),
              streamConfig.getRegion(), InitialPositionInStream.TRIM_HORIZON, kinesisCheckpointInterval,
              StorageLevel.MEMORY_AND_DISK_2(), kinesisRecordMapper, KinesisMessage.class);
    }

    return stream;
  }

  protected <T> JavaDStream<T> unionStreams(List<JavaDStream<T>> dStreamLists, JavaStreamingContext streamingContext) {

    log.info("dStreamLists size "+dStreamLists.size());

    return (dStreamLists.size() > 1) ?
        streamingContext.union(dStreamLists.get(0), dStreamLists.subList(1, dStreamLists.size())) :
        dStreamLists.get(0);
  }

}
