package com.hmhco.api.grading;

import com.google.common.collect.Ordering;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiListingReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Comparator;
import java.util.Objects;

/**
 * Created by nandipatim on 4/14/17.
 */

@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
@ComponentScan("com.hmhco,io.hmheng.grading")
public class GradingServiceApplication {

  @RequestMapping("/")
  String home() {
    return "Spring Grading Running!!";
  }

  public static void main(String[] args) {
    SpringApplication.run(GradingServiceApplication.class, args);
  }
  @Value("${spring.profiles.active}")
  private String environment;

  @Bean
  public Docket gradingApi() {
    boolean enabled= !Objects.equals(environment, "prod");
    ApiInfo info=new ApiInfo("Spring Grading Services", "", "", "", "http://www.hmhco.com", "", "");
    return new Docket(DocumentationType.SWAGGER_2).enable(enabled).
        apiListingReferenceOrdering(Ordering.from(Comparator.comparing(ApiListingReference::getPath))).
        apiInfo(info).select().apis(RequestHandlerSelectors.any()).
        paths(PathSelectors.ant("/v*/**")).build();
  }

  @Bean
  public FilterRegistrationBean timingFilter() {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    TimingFilter timingFilter = new TimingFilter();
    registrationBean.setFilter(timingFilter);
    registrationBean.setOrder(100);
    return registrationBean;
  }

}
