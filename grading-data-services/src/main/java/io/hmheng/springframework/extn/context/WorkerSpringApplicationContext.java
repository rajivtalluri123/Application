package io.hmheng.springframework.extn.context;

import io.hmheng.grading.authorization.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication(scanBasePackageClasses = { WorkerSpringApplicationContext.class,
    AuthorizationService.class }
    ,exclude = {DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class,HibernateJpaAutoConfiguration.class , JmxAutoConfiguration.class})
@EnableConfigurationProperties
@ComponentScan("io.hmheng.grading.streams.grading, io.hmheng.grading.streams.scoring , io.hmheng.grading.rest " +
    ", io.hmheng.grading.authorization, io.hmheng.grading.config, resources ,  io.hmheng.grading.learnosity " +
    ", io.hmheng.grading.streams.kinesis, io.hmheng.grading.streams.config")
public class WorkerSpringApplicationContext {

  private static final Object monitor = new Object();

  private static ConfigurableApplicationContext applicationContext;

  protected static void start(String... profiles) {
    synchronized (monitor) {
      if (applicationContext == null) {
        log.info("Starting Spring context on worker.");
        SpringApplication app = new SpringApplication(WorkerSpringApplicationContext.class);
        app.setAdditionalProfiles(profiles);
        app.setWebEnvironment(false);
        applicationContext = app.run();
        applicationContext.registerShutdownHook();
      }
    }
  }

  public static ApplicationContext getContext(String... profiles) {
    if (applicationContext == null) {
      start(profiles);
    }
    return applicationContext;
  }

}
