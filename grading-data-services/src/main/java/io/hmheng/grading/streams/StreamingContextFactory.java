package io.hmheng.grading.streams;

import io.hmheng.grading.streams.kinesis.model.KinesisMessage;
import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by nandipatim on 1/24/17.
 */
@Component
@Slf4j
public class StreamingContextFactory implements Serializable {
  private static final long serialVersionUID = 1L;

  @Value("${spark.app.env}")
  private String sparkAppEnv;

  @Value("${spark.app.master}")
  private String sparkMaster;

  @Value("${spark.app.home}")
  private String sparkHome;

  @Value("${spark.app.batchMillis}")
  private Long batchMillis;

  @Value("${aws.app.disableCertChecking}")
  private String awsDisableCertChecking;

  @Value("${spark.app.memory.fraction}")
  private String sparkMemoryFraction;

  @Value("${spark.app.memory.storageFraction}")
  private String sparkMemoryStorageFraction;

  @Value("${spark.executor.uri}")
  private String sparkExecutorUri;

  @Value("${spark.executor.memory}")
  private String sparkExecutorMemory;

  @Value("${spark.driver.memory}")
  private String sparkDriverMemory;

  @Value("${spark.app.enableYarnMaster}")
  private Boolean enableYarnMaster;

  @Value("${spark.app.label}")
  private String appName;

  public JavaStreamingContext create() {
    //TODO: All the config has to be moved to yml or config service
    SparkConf conf = new SparkConf();
    System.getProperties().setProperty("com.amazonaws.sdk.disableCertChecking", awsDisableCertChecking);
    conf.setMaster(sparkMaster);
    conf.set("spark.executor.uri", sparkExecutorUri);
    //Change it the local Where spark is installed to
    //Below means you are working Yarn standalone rather than mesos cluster for development.
    //Change it the local Where spark is installed to
    if(enableYarnMaster) {
      conf.setSparkHome(sparkHome);
      conf.set("spark.memory.fraction", sparkMemoryFraction);
      conf.set("spark.memory.storageFraction", sparkMemoryStorageFraction);
      conf.set("spark.driver.memory", sparkDriverMemory);
      conf.set("spark.executor.memory", sparkExecutorMemory);
      conf.set("spark.app.env", sparkAppEnv);
    }

    conf.setAppName(appName);

    //Below should be used when we are going from checkpoint
        //conf.set("spark.app.checkpoint_dir", "/usr/local/Cellar/apache-spark/2.1.0");
        //setIfNotDefined(conf, Constants.CHECKPOINT_DIR_KEY, String.format
        // ("/mnt/efs/service/roles/hmheng-report/spark/%s/checkpoint/", environment));
    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
    conf.registerKryoClasses(new Class[] { KinesisMessage.class });

    return new JavaStreamingContext(new JavaSparkContext(conf), new Duration(batchMillis));
  }

  /**
   * Set a configuration value according to an order of precedence 1. System Property 2. Value passed into the conf 3.
   * Default value
   *
   * @return Spark Conf
   */
  private String setIfNotDefined(SparkConf conf, String key, String value) {
    String setValue = value;
    // Use a value in the conf if set
    if (conf.contains(key)) {
      setValue = conf.get(key);
    } else {
      conf.set(key, setValue);
    }
    return setValue;
  }
}
