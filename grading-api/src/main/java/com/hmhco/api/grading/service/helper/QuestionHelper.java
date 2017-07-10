package com.hmhco.api.grading.service.helper;

import com.hmhco.api.grading.dao.readwrite.QuestionEntityRepository;
import com.hmhco.api.grading.dao.readwrite.ScoreEntityRepository;
import com.hmhco.api.grading.entities.QuestionEntity;
import com.hmhco.api.grading.mapper.viewmapper.QuestionsViewMapper;
import com.hmhco.api.grading.views.QuestionsView;
import com.hmhco.api.grading.views.ScoresView;
import io.hmheng.grading.utils.BeanPropertyUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Created by nandipatim on 5/15/17.
 */
@Component
public class QuestionHelper {

  @Autowired
  private QuestionsViewMapper questionsViewMapper;

  @Autowired
  private QuestionEntityRepository questionEntityRepository;

  @Autowired
  private ScoresHelper scoresHelper;

  @Autowired
  private ScoreEntityRepository scoreEntityRepository;

  public List<QuestionEntity> createorUpdateQuestions( List<QuestionsView> questionEntitiesView){

    List<QuestionEntity> questionEntityList = questionsViewMapper.convert(questionEntitiesView);
    Set<String> questionReference = new HashSet<>();
    List<ScoresView>  responsesViewList = new ArrayList<>();
    List<QuestionEntity> questionEntitiesDB ;

    questionEntitiesView.stream().forEach(view->{
      questionReference.add(view.getQuestionReference());
      List<ScoresView> scoresViews = view.getScores();
      if(!CollectionUtils.isEmpty(scoresViews)) {
        responsesViewList.addAll(view.getScores());
      }
    });

    questionEntitiesDB = questionEntityRepository.findByQuestionReferenceIn(new ArrayList<>(questionReference));

    if(CollectionUtils.isEmpty(questionEntityList)){
      return questionEntitiesDB;
    }

    Set<QuestionEntity> questionEntityViewSet = new HashSet<>(questionEntityList);

    List<QuestionEntity> newEntries = ListUtils.subtract(new ArrayList<>(questionEntityViewSet), questionEntitiesDB);

    questionEntitiesDB.stream().forEach(question -> {

      int indexOfQuestion = questionEntityList.indexOf(question);
      LocalDateTime createDate = question.getCreatedDate();
      QuestionEntity questionEntity = null;
      if (indexOfQuestion != -1) {
        questionEntity = questionEntityList.get(indexOfQuestion);
      }
      if (questionEntity != null) {
        BeanPropertyUtils.copyPropertiesWithOnlyPopulated(questionEntity, question);
        question.setQuestionReference(question.getQuestionReference());
        if (createDate == null) {
          question.setCreatedDate(LocalDateTime.now());
        }
      }

    });

    if(!CollectionUtils.isEmpty(responsesViewList)) {
      scoreEntityRepository.save(scoresHelper.createorUpdateScores(responsesViewList));
    }

    if(!CollectionUtils.isEmpty(newEntries)){

      Set<QuestionEntity> questionEntitySet = new HashSet<>(newEntries);

      questionEntitiesDB.addAll(questionEntitySet);
    }

    return questionEntitiesDB;
  }

}
