package com.hmhco.api.grading.mapper.viewmapper;


import com.google.gson.reflect.TypeToken;
import com.hmhco.api.grading.controller.utils.MapperUtil;
import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.readonly.ActivityStudentQuestionViewEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.getresponse.StudentQuestionGetView;
import com.hmhco.api.grading.views.getresponse.StudentScoreGetView;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by srikanthk on 5/12/17.
 */

@Component
public class ActivityStudentQuestion implements SingleEntityMapper<ActivityStudentQuestionViewEntity,StudentQuestionGetView> {


    @Autowired
    private ActivityStudentScore activityStudentScore;
    
	@Autowired
	MapperUtil mapperUtil;

    @Override
    public StudentQuestionGetView convert(ActivityStudentQuestionViewEntity entity) {

        StudentQuestionGetView studentQuestionGetView = new StudentQuestionGetView();
        BeanUtils.copyProperties(entity,studentQuestionGetView );
        List<StudentScoreGetView> scores = activityStudentScore.convert(entity.getResponses());
        if(scores != null )
            studentQuestionGetView.setResponses(scores);
        String actualResponseStr = entity.getActualResponse();
        Object actualResponseObj = mapperUtil.transformToObject(actualResponseStr);
        studentQuestionGetView.setActualResponse(actualResponseObj);

        return studentQuestionGetView;

    }

    @Override
    public boolean supports(Class<? extends AbstractEntity> clazz) {
        return   ActivityStudentQuestionViewEntity.class.equals(clazz);
    }


}