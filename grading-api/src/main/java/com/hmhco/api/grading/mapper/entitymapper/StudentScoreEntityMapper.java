package com.hmhco.api.grading.mapper.entitymapper;

import com.hmhco.api.grading.controller.utils.MapperUtil;
import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.StudentScoreEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.StudentScoreView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by nandipatim on 5/15/17.
 */
@Component
public class StudentScoreEntityMapper implements SingleEntityMapper<StudentScoreEntity,StudentScoreView> {

  @Autowired
  MapperUtil mapperUtil;

  @Override
  public StudentScoreView convert( StudentScoreEntity entity){
    StudentScoreView studentScoreView = new StudentScoreView();
    BeanUtils.copyProperties(entity, studentScoreView);
    String valueStr = entity.getValue();
    Object valueObj = mapperUtil.transformToObject(valueStr);
    studentScoreView.setValue(valueObj);
    return studentScoreView;
  }

  @Override
  public boolean supports(Class<? extends AbstractEntity> clazz){
    return StudentScoreEntity.class.equals(clazz);

  }
}
