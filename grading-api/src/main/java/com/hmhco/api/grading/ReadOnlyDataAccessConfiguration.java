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
    entityManagerFactoryRef = "readOnlyEntityManagerFactory",
    transactionManagerRef = "readOnlyTransactionManager",
    basePackages = "com.hmhco.api.grading.dao.readonly")
public class ReadOnlyDataAccessConfiguration {

  @Autowired
  @Qualifier("readOnlyEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean readOnlyEntityManagerFactory;

  @Bean
  @Qualifier("readOnlyTransactionManager")
  public PlatformTransactionManager readOnlyTransactionManager() {
    return new JpaTransactionManager(readOnlyEntityManagerFactory.getObject());
  }
}
