package com.hmhco.api.grading.mapper.viewmapper;

import com.hmhco.api.grading.entities.QuestionEntity;
import com.hmhco.api.grading.views.AbstractView;
import com.hmhco.api.grading.views.QuestionsView;
import io.hmheng.grading.utils.BeanPropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by srikanthk on 4/30/17.
 */
@Component
public class QuestionsViewMapper implements SingleViewMapper<QuestionsView, QuestionEntity> {



    @Autowired
    private ScoreViewMapper scoreViewMapper;

    @Override
    public QuestionEntity convert(QuestionsView view) {
        QuestionEntity questionEntity = new QuestionEntity();
        BeanPropertyUtils.copyPropertiesWithOnlyPopulated(view, questionEntity);
        return questionEntity;
    }


    @Override
    public boolean supports(Class<? extends AbstractView> clazz) {
        return QuestionsView.class.equals(clazz);
    }
}
