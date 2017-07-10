package com.hmhco.api.grading.mapper.entitymapper;

import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.QuestionEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.QuestionsView;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Created by srikanthk on 4/30/17.
 */
@Component
public class QuestionsEntityMapper implements SingleEntityMapper<QuestionEntity, QuestionsView> {

    @Override
    public QuestionsView convert(QuestionEntity entity) {
        QuestionsView questionView = new QuestionsView();
        BeanUtils.copyProperties(entity , questionView);
        return questionView;
    }

    @Override
    public boolean supports(Class<? extends AbstractEntity> clazz) {
        return QuestionEntity.class.equals(clazz);
    }
}
