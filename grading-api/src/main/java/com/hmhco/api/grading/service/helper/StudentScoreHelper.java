package com.hmhco.api.grading.service.helper;

import com.hmhco.api.grading.entities.StudentQuestionEntity;
import com.hmhco.api.grading.entities.StudentScoreEntity;
import io.hmheng.grading.utils.BeanPropertyUtils;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Created by nandipatim on 5/15/17.
 */
@Component
public class StudentScoreHelper {

  public List<StudentScoreEntity> getOrCreateStudentScores(StudentQuestionEntity questionEntityfromDB,
                                                            StudentQuestionEntity questionEntityfromView){

    List<StudentScoreEntity> studentScoreEntitiesFromView = questionEntityfromView.getStudentScores();
    List<StudentScoreEntity> studentScoreEntitiesFromDB = questionEntityfromDB.getStudentScores();

    if(studentScoreEntitiesFromView == null) {
      return studentScoreEntitiesFromDB;
    }

    studentScoreEntitiesFromView.stream().forEach(score -> {
      score.setStudentSession(questionEntityfromDB.getStudentItems().getStudentSession());
    });

    if(!CollectionUtils.isEmpty(studentScoreEntitiesFromDB)) {
      studentScoreEntitiesFromDB.stream().forEach(scoreEntity -> {
        int indexOfScore = studentScoreEntitiesFromView.indexOf(scoreEntity);
        StudentScoreEntity studentScoreEntity = null;
        if (indexOfScore != -1) {
          studentScoreEntity = studentScoreEntitiesFromView.get(indexOfScore);
        }
        if (studentScoreEntity != null) {
          String refid = scoreEntity.getResponseId();
          BeanPropertyUtils.copyPropertiesWithOnlyPopulated(studentScoreEntity, scoreEntity);
          scoreEntity.setStudentItems(questionEntityfromDB.getStudentItems());
          scoreEntity.setStudentSession(scoreEntity.getStudentItems().getStudentSession());
          scoreEntity.setStudentQuestions(questionEntityfromDB);
          if(scoreEntity.getCreatedDate() == null){
            scoreEntity.setCreatedDate(LocalDateTime.now());
          }
          scoreEntity.setResponseId(refid);
        }
      });
    }

    List<StudentScoreEntity> newEntities = ListUtils.subtract(studentScoreEntitiesFromView, studentScoreEntitiesFromDB) ;
    if(!CollectionUtils.isEmpty(newEntities)) {
      newEntities.stream().forEach(scoreEntity -> {
        scoreEntity.setStudentQuestions(questionEntityfromDB);
        scoreEntity.setStudentItems(questionEntityfromDB.getStudentItems());
      });

      studentScoreEntitiesFromDB.addAll(newEntities);
    }

    return studentScoreEntitiesFromDB;

  }

}
