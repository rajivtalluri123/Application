package com.hmhco.api.grading;


import com.hmhco.api.grading.views.StudentItemView;
import com.hmhco.api.grading.views.StudentQuestionView;
import com.hmhco.api.grading.views.StudentScoreView;
import com.hmhpub.common.instrumentation.rest.RequestResponseLoggingFilter;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
@EnableJpaAuditing
public class RestConfiguration extends RepositoryRestMvcConfiguration {

  @Bean
  @Override
  @ConfigurationProperties(prefix = "spring.data.rest")
  public RepositoryRestConfiguration config() {
    RepositoryRestConfiguration config = super.config();
    config.exposeIdsFor(StudentItemView.class, StudentQuestionView.class, StudentScoreView.class);
    return config;
  }

  @Autowired
  public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    return new MappingJackson2HttpMessageConverter(jackson2ObjectMapperBuilder.build());
  }

  @Bean
  public FilterRegistrationBean requestResponseLoggingFilterBean() {
    RequestResponseLoggingFilter loggingFilter = new RequestResponseLoggingFilter();
    loggingFilter.setLogRequestPayload(true);
    loggingFilter.setLogResponsePayload(false);
    loggingFilter.setPayloadLimit(4096);
    loggingFilter.setLogRequestBeforeProcessing(true);

    FilterRegistrationBean loggingBean = new FilterRegistrationBean();
    loggingBean.setFilter(loggingFilter);
    loggingBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    loggingBean.setUrlPatterns(Arrays.asList("/v*/*"));

    return loggingBean;
  }

}