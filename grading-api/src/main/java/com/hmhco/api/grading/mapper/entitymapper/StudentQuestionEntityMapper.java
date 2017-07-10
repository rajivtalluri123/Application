package com.hmhco.api.grading.mapper.entitymapper;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.StudentQuestionEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.StudentQuestionView;

/**
 * Created by nandipatim on 5/15/17.
 */
@Component
public class StudentQuestionEntityMapper implements SingleEntityMapper<StudentQuestionEntity,StudentQuestionView> {

  @Override
  public StudentQuestionView convert( StudentQuestionEntity entity){
    StudentQuestionView studentQuestionView = new StudentQuestionView();
    BeanUtils.copyProperties(entity, studentQuestionView);
    String actualResponseStr = entity.getActualResponse();
    Object actualResponseObj = null;
    try{
    actualResponseObj = new ObjectMapper().readValue(actualResponseStr, Object.class);
    }catch(Exception e){}
    studentQuestionView.setActualResponse(actualResponseObj);
    return studentQuestionView;
  }

  @Override
  public boolean supports(Class<? extends AbstractEntity> clazz){
    return StudentQuestionEntity.class.equals(clazz);

  }

}
