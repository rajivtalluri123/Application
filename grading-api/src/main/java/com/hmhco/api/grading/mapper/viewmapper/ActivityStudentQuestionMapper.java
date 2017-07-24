package com.hmhco.api.grading.mapper.viewmapper;




import com.hmhco.api.grading.controller.utils.MapperUtil;
import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.readonly.ActivityStudentQuestionViewEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.getresponse.StudentQuestionGetView;
import com.hmhco.api.grading.views.getresponse.StudentScoreGetView;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * Created by srikanthk on 5/12/17.
 */

@Component
public class ActivityStudentQuestionMapper implements SingleEntityMapper<ActivityStudentQuestionViewEntity,StudentQuestionGetView> {

    @Autowired
    MapperUtil mapperUtil;

    @Autowired
    private ActivityStudentScore activityStudentScore;

    @Override
    public StudentQuestionGetView convert(ActivityStudentQuestionViewEntity entity) {

        StudentQuestionGetView studentQuestionGetView = new StudentQuestionGetView();
        BeanUtils.copyProperties(entity,studentQuestionGetView );
        List<StudentScoreGetView> scores = activityStudentScore.convert(entity.getResponses());
        if(scores != null ) {
            studentQuestionGetView.setResponses(scores);

        }


        String actualResponseStr = entity.getActualResponse();
        Object actualResponseObj = mapperUtil.transformToObject(actualResponseStr);
        studentQuestionGetView.setActualResponse(actualResponseObj);


        Integer maxScore = null;
        for (StudentScoreGetView score : studentQuestionGetView.getResponses()) {
            if (score.getScore() != null) {
                maxScore = maxScore == null ? score.getMaxScore() : maxScore + score.getMaxScore();
            }
        }
        studentQuestionGetView.setMaxScore(maxScore);

        return studentQuestionGetView;
    }

    @Override
    public boolean supports(Class<? extends AbstractEntity> clazz) {
        return   ActivityStudentQuestionViewEntity.class.equals(clazz);
    }


}