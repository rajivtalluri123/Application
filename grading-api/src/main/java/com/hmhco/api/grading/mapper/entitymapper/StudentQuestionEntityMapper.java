package com.hmhco.api.grading.mapper.entitymapper;

import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.StudentQuestionEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.StudentQuestionView;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Created by nandipatim on 5/15/17.
 */
@Component
public class StudentQuestionEntityMapper implements SingleEntityMapper<StudentQuestionEntity,StudentQuestionView> {

  @Override
  public StudentQuestionView convert( StudentQuestionEntity entity){
    StudentQuestionView studentQuestionView = new StudentQuestionView();
    BeanUtils.copyProperties(entity, studentQuestionView);
    return studentQuestionView;
  }

  @Override
  public boolean supports(Class<? extends AbstractEntity> clazz){
    return StudentQuestionEntity.class.equals(clazz);

  }

}
