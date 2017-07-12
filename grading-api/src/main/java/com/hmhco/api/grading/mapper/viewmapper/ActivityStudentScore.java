package com.hmhco.api.grading.mapper.viewmapper;

import com.hmhco.api.grading.controller.utils.MapperUtil;
import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.readonly.ActivityStudentScoreViewEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.getresponse.StudentScoreGetView;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by srikanthk on 5/12/17.
 */

@Component
public class ActivityStudentScore implements SingleEntityMapper<ActivityStudentScoreViewEntity, StudentScoreGetView> {


    @Override
    public StudentScoreGetView convert(ActivityStudentScoreViewEntity entity) {
        StudentScoreGetView  studentScoreGetView = new StudentScoreGetView();
        BeanUtils.copyProperties(entity,studentScoreGetView );
        String valueStr= entity.getValue();
        Object value= MapperUtil.transformToObject(valueStr);
        studentScoreGetView.setValue(value);
        return studentScoreGetView;
    }

    @Override
    public boolean supports(Class<? extends AbstractEntity> clazz) {
        return ActivityStudentScoreViewEntity.class.equals(clazz);
    }
}