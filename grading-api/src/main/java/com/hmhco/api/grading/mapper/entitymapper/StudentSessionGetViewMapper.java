package com.hmhco.api.grading.mapper.entitymapper;

import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.StudentSessionEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.getresponse.StudentSessionGetView;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Created by nandipatim on 6/6/17.
 */
@Component
public class StudentSessionGetViewMapper implements SingleEntityMapper<StudentSessionEntity , StudentSessionGetView> {

  @Override
  public StudentSessionGetView convert(StudentSessionEntity entity) {
    StudentSessionGetView studentSessionView = new StudentSessionGetView();
      BeanUtils.copyProperties(entity, studentSessionView);
      return studentSessionView;
  }

  @Override
  public boolean supports(Class<? extends AbstractEntity> clazz) {
      return StudentSessionEntity.class.equals(clazz);
  }
}
