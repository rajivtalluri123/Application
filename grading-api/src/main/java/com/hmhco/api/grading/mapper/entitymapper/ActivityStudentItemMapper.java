package com.hmhco.api.grading.mapper.entitymapper;

import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.readonly.ActivityStudentItemViewEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.mapper.viewmapper.ActivityStudentQuestionMapper;
import com.hmhco.api.grading.views.getresponse.StudentItemGetView;
import com.hmhco.api.grading.views.getresponse.StudentQuestionGetView;
import com.hmhco.api.grading.views.getresponse.StudentScoreGetView;
import io.hmheng.grading.utils.Status;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by srikanthk on 5/12/17.
 */

@Component
public class ActivityStudentItemMapper implements SingleEntityMapper<ActivityStudentItemViewEntity, StudentItemGetView> {

    @Autowired
    private ActivityStudentQuestionMapper activityStudentQuestionMapper;

    public List<StudentItemGetView> convert(Collection<ActivityStudentItemViewEntity> entities , Boolean excludeQuestionsAndScores) {
      List<StudentItemGetView> studentItemGetViews = new ArrayList<>();
       entities.stream().forEach(activityStudentItemViewEntity ->
           studentItemGetViews.add(convert(activityStudentItemViewEntity , excludeQuestionsAndScores)));

      return studentItemGetViews;
    }

    public StudentItemGetView convert(ActivityStudentItemViewEntity entity , Boolean  excludeQuestionsAndScores){

      StudentItemGetView studentItemGetView = new StudentItemGetView();
      BeanUtils.copyProperties(entity,studentItemGetView );

      List<StudentQuestionGetView> questions = activityStudentQuestionMapper.convert(entity.getQuestions());
      if(questions!= null )
      {
        studentItemGetView.setQuestions(questions);
        studentItemGetView.setStatus(getItemStatus(questions));
        Integer maxScore = rollupMaxScore(questions);
        studentItemGetView.setMaxScore(maxScore);
      }

      if (excludeQuestionsAndScores) {
        studentItemGetView.setQuestions(null);
      }

      return studentItemGetView;
    }

    @Override
    public StudentItemGetView convert(ActivityStudentItemViewEntity entity) {

      return convert(entity , false);
    }

    public Status getItemStatus(List<StudentQuestionGetView> questions){

        Status status = Status.NOT_SCORED;


         for(StudentQuestionGetView question : questions){
             int responsecnt = 0;
             for(StudentScoreGetView score : question.getResponses()){

                 if(score.getScore() != null || score.isAutomarkable()){
                     responsecnt++;
                 }

             }
             if(responsecnt > 0)
                    status = (responsecnt == question.getResponses().size() ? Status.SCORING_COMPLETED: Status
                     .SCORING_IN_PROGRESS);
         }


         return status;

    }

    public Integer rollupMaxScore(List<StudentQuestionGetView> questions) {
        Integer maxScore = null;
        for (StudentQuestionGetView question : questions){
            if (question.getMaxScore() != null) {
                maxScore = maxScore == null ? question.getMaxScore() : maxScore + question.getMaxScore();
            }
        }
        return maxScore;
    }

    @Override
    public boolean supports(Class<? extends AbstractEntity> clazz) {
        return ActivityStudentItemViewEntity.class.equals(clazz);
    }
}