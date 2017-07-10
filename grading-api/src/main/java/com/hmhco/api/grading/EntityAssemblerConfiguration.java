package com.hmhco.api.grading;

import com.hmhco.api.grading.assembler.EntityAssemblerProvider;
import com.hmhco.api.grading.assembler.StudentItemAssembler;
import com.hmhco.api.grading.views.getresponse.StudentItemGetView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityAssemblerConfiguration {

  @Bean
  public StudentItemAssembler studentItemAssembler(){
    return new StudentItemAssembler();
  }

  @Bean
  public EntityAssemblerProvider entityAssemblerProvider() {
    EntityAssemblerProvider entityAssemblerProvider = new EntityAssemblerProvider();

    entityAssemblerProvider.addAssembler(StudentItemGetView.class, studentItemAssembler());
    return entityAssemblerProvider;
  }
}
