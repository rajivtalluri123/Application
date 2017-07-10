package com.hmhco.api.grading.mapper.entitymapper;

import com.hmhco.api.grading.entities.StudentItemEntity;
import com.hmhco.api.grading.entities.StudentQuestionEntity;
import com.hmhco.api.grading.entities.StudentScoreEntity;
import com.hmhco.api.grading.mapper.viewmapper.StudentQuestionViewMapper;
import com.hmhco.api.grading.mapper.viewmapper.SingleViewMapper;
import com.hmhco.api.grading.views.AbstractView;
import com.hmhco.api.grading.views.StudentItemView;
import com.hmhco.api.grading.views.StudentQuestionView;
import io.hmheng.grading.utils.BeanPropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srikanthk on 5/3/17.
 */
@Component
public class StudentItemViewMapper  implements SingleViewMapper<StudentItemView, StudentItemEntity> {


    @Autowired
    StudentQuestionViewMapper studentQuestionViewMapper;

    @Override
    public StudentItemEntity convert(StudentItemView view) {
        StudentItemEntity studentItemEntity = new StudentItemEntity();
        BeanPropertyUtils.copyPropertiesWithOnlyPopulated(view, studentItemEntity);
        if(!CollectionUtils.isEmpty(view.getQuestions())) {
            studentItemEntity.setStudentQuestions(getStudentQuestions(view.getQuestions(), studentItemEntity));
        }

        return studentItemEntity;
    }

    private List<StudentQuestionEntity> getStudentQuestions(List<StudentQuestionView> studentQuestions, StudentItemEntity studentItemEntity){
        List<StudentQuestionEntity> questionEntities = new ArrayList<>();
        if(!CollectionUtils.isEmpty(studentQuestions)){
            studentQuestions.stream().forEach(questionsView -> {
                StudentQuestionEntity studentQuestionEntity = studentQuestionViewMapper.convert(questionsView);
                studentQuestionEntity.setStudentItems(studentItemEntity);
                studentQuestionEntity.setStudentScores(getStudentScores(studentQuestionEntity.getStudentScores(), studentItemEntity));
                questionEntities.add(studentQuestionEntity);
            });
        }

        return questionEntities;

    }

    private List<StudentScoreEntity> getStudentScores(List<StudentScoreEntity> studentScores, StudentItemEntity studentItemEntity){

        List<StudentScoreEntity> scoreEntities = new ArrayList<>();
        if(!CollectionUtils.isEmpty(studentScores)){
            studentScores.stream().forEach(scoresentity -> {
                scoresentity.setStudentItems(studentItemEntity);
                scoreEntities.add(scoresentity);
            });
        }

        return scoreEntities;

    }


    @Override
    public boolean supports(Class<? extends AbstractView> clazz) {
        return StudentItemView.class.equals(clazz);
    }}
