package com.hmhco.api.grading.mapper.entitymapper;

import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.StudentItemEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.StudentItemView;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Created by nandipatim on 5/15/17.
 */
@Component
public class StudentItemEntityMapper implements SingleEntityMapper<StudentItemEntity,StudentItemView> {

  @Override
  public StudentItemView convert( StudentItemEntity entity){
    StudentItemView studentItemView = new StudentItemView();
    BeanUtils.copyProperties(entity, studentItemView);
    return studentItemView;
  }

  @Override
  public boolean supports(Class<? extends AbstractEntity> clazz){
    return StudentItemEntity.class.equals(clazz);

  }
}
