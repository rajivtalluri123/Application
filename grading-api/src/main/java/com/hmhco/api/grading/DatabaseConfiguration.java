package com.hmhco.api.grading;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableConfigurationProperties(JpaProperties.class)
public class DatabaseConfiguration {

  @Value("${spring.jpa.hibernate.show_sql:false}")
  private boolean showSql;

  @Value("${spring.jpa.hibernate.format_sql:false}")
  private boolean formatSql;

  @Autowired
  public JpaProperties jpaProperties;

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  @Qualifier("scoringDatasource")
  public DataSource scoringDatasource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    adapter.setShowSql(this.jpaProperties.isShowSql());
    adapter.setDatabase(this.jpaProperties.getDatabase());
    adapter.setDatabasePlatform(this.jpaProperties.getDatabasePlatform());
    adapter.setGenerateDdl(this.jpaProperties.isGenerateDdl());
    return adapter;
  }

  @Bean
  public EntityManagerFactoryBuilder entityManagerFactoryBuilder(JpaVendorAdapter jpaVendorAdapter) {
    return new EntityManagerFactoryBuilder(jpaVendorAdapter, jpaProperties.getProperties(), null);
  }

  @Bean
  @Primary
  @Qualifier("readOnlyEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean readOnlyEntityManagerFactory(
      EntityManagerFactoryBuilder factoryBuilder) {
    return buildEntityManagerFactory(factoryBuilder, scoringDatasource());
  }

  @Bean
  @Qualifier("readWriteEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean readWriteEntityManagerFactory(
      EntityManagerFactoryBuilder factoryBuilder) {
    return buildEntityManagerFactory(factoryBuilder, scoringDatasource());
  }

  private LocalContainerEntityManagerFactoryBean buildEntityManagerFactory(EntityManagerFactoryBuilder factoryBuilder,
      DataSource dataSource) {

    Map<String, Object> vendorProperties = getVendorProperties(dataSource);
    return factoryBuilder.dataSource(dataSource).packages("com.hmhco.api.grading").properties(vendorProperties)
        .jta(false).build();
  }

  private Map<String, Object> getVendorProperties(DataSource dataSource) {
    Map<String, Object> vendorProperties = new LinkedHashMap<String, Object>();
    vendorProperties.put("hibernate.show_sql", showSql);
    vendorProperties.put("hibernate.format_sql", formatSql);
    vendorProperties.putAll(jpaProperties.getHibernateProperties(dataSource));
    return vendorProperties;
  }
}
