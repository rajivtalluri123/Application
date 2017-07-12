package com.hmhco.api.grading.mapper.viewmapper;

import com.hmhco.api.grading.entities.StudentScoreEntity;
import com.hmhco.api.grading.views.AbstractView;
import com.hmhco.api.grading.views.StudentScoreView;
import io.hmheng.grading.utils.BeanPropertyUtils;
import org.springframework.stereotype.Component;
import org.codehaus.jackson.map.ObjectMapper;
import com.hmhco.api.grading.controller.utils.MapperUtil;

/**
 * Created by srikanthk on 5/3/17.
 */
@Component
public class StudentScoreViewMapper implements SingleViewMapper<StudentScoreView, StudentScoreEntity> {

    @Override
    public StudentScoreEntity convert(StudentScoreView view) {
        StudentScoreEntity studentScoreEntity = new StudentScoreEntity();
        BeanPropertyUtils.copyPropertiesWithOnlyPopulated(view, studentScoreEntity);
		String value = MapperUtil.transformToString(view.getValue());
        studentScoreEntity.setValue(value);

        return studentScoreEntity;

    }
    @Override
    public boolean supports(Class<? extends AbstractView> clazz) {
        return StudentScoreView.class.equals(clazz);
    }
}