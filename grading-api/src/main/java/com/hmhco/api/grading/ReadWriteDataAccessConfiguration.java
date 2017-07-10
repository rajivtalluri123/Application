package com.hmhco.api.grading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = "readWriteEntityManagerFactory",
    transactionManagerRef = "readWriteTransactionManager",
    basePackages = "com.hmhco.api.grading.dao.readwrite")
public class ReadWriteDataAccessConfiguration {

  @Autowired
  @Qualifier("readWriteEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean readWriteEntityManagerFactory;

  @Bean
  @Qualifier("readWriteTransactionManager")
  public PlatformTransactionManager readWriteTransactionManager() {
    return new JpaTransactionManager(readWriteEntityManagerFactory.getObject());
  }
}
