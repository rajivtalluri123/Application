package com.hmhco.api.grading.mapper;

import com.hmhco.api.grading.entities.ActivityEntity;
import com.hmhco.api.grading.entities.ActivityItemScoreEntity;
import com.hmhco.api.grading.entities.ItemEntity;
import com.hmhco.api.grading.entities.QuestionEntity;
import com.hmhco.api.grading.entities.ScoreEntity;
import com.hmhco.api.grading.entities.StudentItemEntity;
import com.hmhco.api.grading.entities.StudentQuestionEntity;
import com.hmhco.api.grading.entities.StudentScoreEntity;
import com.hmhco.api.grading.entities.StudentSessionEntity;
import com.hmhco.api.grading.views.ActivityItemScoreView;
import com.hmhco.api.grading.views.ActivityView;
import com.hmhco.api.grading.views.ItemsView;
import com.hmhco.api.grading.views.QuestionsView;
import com.hmhco.api.grading.views.ScoresView;
import com.hmhco.api.grading.views.StudentItemView;
import com.hmhco.api.grading.views.StudentActivitySessionView;
import com.hmhco.api.grading.views.StudentQuestionView;
import com.hmhco.api.grading.views.StudentScoreView;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityMapperConfiguration {
  @Bean
  @Qualifier("genericEntityMapper")
  public EntityMapper genericEntityMapper() {
    GenericEntityMapperImpl mapper = new GenericEntityMapperImpl();

    mapper.addClassMapping(StudentSessionEntity.class , StudentActivitySessionView.class);
    mapper.addClassMapping(StudentItemEntity.class , StudentItemView.class);
    mapper.addClassMapping(ActivityEntity.class , ActivityView.class);
    mapper.addClassMapping(ActivityItemScoreEntity.class , ActivityItemScoreView.class);
    mapper.addClassMapping(ItemEntity.class , ItemsView.class);
    mapper.addClassMapping(QuestionEntity.class , QuestionsView.class);
    mapper.addClassMapping(ScoreEntity.class , ScoresView.class);
    mapper.addClassMapping(StudentQuestionEntity.class , StudentQuestionView.class);
    mapper.addClassMapping(StudentScoreEntity.class , StudentScoreView.class);

    return mapper;
  }
}

