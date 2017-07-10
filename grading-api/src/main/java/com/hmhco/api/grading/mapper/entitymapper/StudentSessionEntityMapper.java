package com.hmhco.api.grading.mapper.entitymapper;

import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.StudentSessionEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.StudentActivitySessionView;
import com.hmhco.api.grading.views.StudentSessionView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class StudentSessionEntityMapper implements SingleEntityMapper<StudentSessionEntity , StudentSessionView> {

  @Override
  public StudentSessionView convert(StudentSessionEntity entity) {
    StudentSessionView studentSessionView = new StudentSessionView();
    BeanUtils.copyProperties(entity , studentSessionView);
    return studentSessionView;
  }

  @Override
  public boolean supports(Class<? extends AbstractEntity> clazz) {
    return StudentSessionEntity.class.equals(clazz);
  }
}
