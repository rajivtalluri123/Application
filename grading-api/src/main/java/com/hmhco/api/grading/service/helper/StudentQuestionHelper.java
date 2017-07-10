package com.hmhco.api.grading.service.helper;

import com.hmhco.api.grading.entities.StudentItemEntity;
import com.hmhco.api.grading.entities.StudentQuestionEntity;
import com.hmhco.api.grading.entities.StudentScoreEntity;
import io.hmheng.grading.utils.BeanPropertyUtils;
import java.util.List;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Created by nandipatim on 5/15/17.
 */
@Component
public class StudentQuestionHelper {

  @Autowired
  private StudentScoreHelper studentScoreHelper;

  public List<StudentQuestionEntity> getOrCreateStudentQuestionEntity(StudentItemEntity studentItemEntityFromDB,
                                                                      StudentItemEntity studentItemEntityFromView) {

    List<StudentQuestionEntity> studentQuestionEntitiesFromView = studentItemEntityFromView.getStudentQuestions();
    List<StudentQuestionEntity> studentQuestionEntitiesFromDB = studentItemEntityFromDB.getStudentQuestions();

    if (studentQuestionEntitiesFromView == null) {
      return studentQuestionEntitiesFromDB;
    }

    if (!CollectionUtils.isEmpty(studentQuestionEntitiesFromDB)) {
      studentQuestionEntitiesFromDB.stream().forEach(questionEntity -> {
        int indexOfQuestion = studentQuestionEntitiesFromView.indexOf(questionEntity);
        StudentQuestionEntity studentQuestionEntity = null;
        if (indexOfQuestion != -1) {
          studentQuestionEntity = studentQuestionEntitiesFromView.get(indexOfQuestion);
        }
        if (studentQuestionEntity != null) {
          List<StudentScoreEntity> studentScoreEntities = questionEntity.getStudentScores();
          studentQuestionEntity.setRefId(questionEntity.getRefId());
          BeanPropertyUtils.copyPropertiesWithOnlyPopulated(studentQuestionEntity, questionEntity);
          questionEntity.setStudentScores(studentScoreEntities);
          questionEntity.setStudentItems(studentItemEntityFromDB);
          questionEntity.setStudentScores(studentScoreHelper.getOrCreateStudentScores(questionEntity, studentQuestionEntity));
        }
      });
    }

    List<StudentQuestionEntity> newEntities = ListUtils.subtract(studentQuestionEntitiesFromView, studentQuestionEntitiesFromDB);
    if(!CollectionUtils.isEmpty(newEntities))

    {
      newEntities.stream().forEach(question -> {
        question.setStudentItems(studentItemEntityFromDB);

      });

      studentQuestionEntitiesFromDB.addAll(newEntities);
    }


    return studentQuestionEntitiesFromDB;
  }
}
