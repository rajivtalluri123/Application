package com.hmhco.api.grading.mapper.viewmapper;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.readonly.ActivityStudentScoreViewEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.getresponse.StudentScoreGetView;

/**
 * Created by srikanthk on 5/12/17.
 */

@Component
public class ActivityStudentScore implements SingleEntityMapper<ActivityStudentScoreViewEntity, StudentScoreGetView> {


    @Override
    public StudentScoreGetView convert(ActivityStudentScoreViewEntity entity) {
        StudentScoreGetView  studentScoreGetView = new StudentScoreGetView();
        BeanUtils.copyProperties(entity,studentScoreGetView );
        String valueStr = entity.getValue();
        
        if(valueStr != null && valueStr.length()>0){
	        Object value = null;
			try {
				value = new ObjectMapper().readValue(valueStr, Object.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			studentScoreGetView.setValue(value);
        }
        return studentScoreGetView;
    }

    @Override
    public boolean supports(Class<? extends AbstractEntity> clazz) {
        return ActivityStudentScoreViewEntity.class.equals(clazz);
    }
}