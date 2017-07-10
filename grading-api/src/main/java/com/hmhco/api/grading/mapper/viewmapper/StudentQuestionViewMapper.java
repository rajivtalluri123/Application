package com.hmhco.api.grading.mapper.viewmapper;

import com.hmhco.api.grading.entities.StudentQuestionEntity;
import com.hmhco.api.grading.entities.StudentScoreEntity;
import com.hmhco.api.grading.mapper.EntityMapper;
import com.hmhco.api.grading.views.AbstractView;
import com.hmhco.api.grading.views.StudentQuestionView;
import com.hmhco.api.grading.views.StudentScoreView;
import io.hmheng.grading.utils.BeanPropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srikanthk on 5/3/17.
 */
@Component
public class StudentQuestionViewMapper  implements SingleViewMapper<StudentQuestionView, StudentQuestionEntity> {


    @Autowired
    @Qualifier("genericEntityMapper")
    private EntityMapper entityMapper;

    @Override
    public StudentQuestionEntity convert(StudentQuestionView view) {
        StudentQuestionEntity studentQuestionEntity = new StudentQuestionEntity();
        BeanPropertyUtils.copyPropertiesWithOnlyPopulated(view, studentQuestionEntity);
        if(!CollectionUtils.isEmpty(view.getResponses())) {
            studentQuestionEntity.setStudentScores(getStudentScores(view.getResponses(), studentQuestionEntity));
        }
        return studentQuestionEntity;
    }


    private List<StudentScoreEntity> getStudentScores(List<StudentScoreView> studentScoresView,StudentQuestionEntity studentQuestionEntity ){
        List<StudentScoreEntity> studentScoreEntities = new ArrayList<>();
        if(!CollectionUtils.isEmpty(studentScoresView)){
            studentScoresView.stream().forEach(studentScores->{
                StudentScoreEntity  studentScoreEntity = entityMapper.convert(studentScores);
                studentScoreEntity.setStudentQuestions(studentQuestionEntity);
                studentScoreEntity.setStudentItems(studentQuestionEntity.getStudentItems());
                studentScoreEntities.add(studentScoreEntity);
            });
        }
        return studentScoreEntities;
    }


    @Override
    public boolean supports(Class<? extends AbstractView> clazz) {
        return false;
    }
}
