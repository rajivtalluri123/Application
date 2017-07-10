package com.hmhco.api.grading.mapper.viewmapper;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.readonly.ActivityStudentQuestionViewEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.getresponse.StudentQuestionGetView;
import com.hmhco.api.grading.views.getresponse.StudentScoreGetView;

/**
 * Created by srikanthk on 5/12/17.
 */

@Component
public class ActivityStudentQuestion implements SingleEntityMapper<ActivityStudentQuestionViewEntity,StudentQuestionGetView> {


    @Autowired
    private ActivityStudentScore activityStudentScore;

    @Override
    public StudentQuestionGetView convert(ActivityStudentQuestionViewEntity entity) {

        StudentQuestionGetView studentQuestionGetView = new StudentQuestionGetView();
        BeanUtils.copyProperties(entity,studentQuestionGetView );
        List<StudentScoreGetView> scores = activityStudentScore.convert(entity.getResponses());
        if(scores != null )
            studentQuestionGetView.setResponses(scores);
        
        String actualResponseStr = entity.getActualResponse();
        Object actualResponseObj = null;
        try{
        actualResponseObj = new ObjectMapper().readValue(actualResponseStr, Object.class);
        }catch(Exception e){}
        studentQuestionGetView.setActualResponse(actualResponseObj);

        return studentQuestionGetView;

    }

    @Override
    public boolean supports(Class<? extends AbstractEntity> clazz) {
        return   ActivityStudentQuestionViewEntity.class.equals(clazz);
    }
}