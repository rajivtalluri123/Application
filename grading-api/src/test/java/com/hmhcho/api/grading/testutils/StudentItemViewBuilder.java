package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.views.StudentItemView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by srikanthk on 5/2/17.
 */
public class StudentItemViewBuilder {


    private StudentItemView studentItemView = new StudentItemView();

    private List<StudentItemView> studentItemViewList = new ArrayList<>();


    public StudentItemView buildView(){return studentItemView;}

    public List<StudentItemView> buildViewList(Integer count ){return studentItemViewList; }

    StudentQuestionViewBuilder studentQuestionViewBuilder = new StudentQuestionViewBuilder();

    private StudentScoreViewBuilder studentScoreViewBuilder = new StudentScoreViewBuilder();

    public StudentItemViewBuilder withAll(){

        studentItemView.setUserFlagged(true);
        studentItemView.setTime(100);
        studentItemView.setItemReference("itemReference");
        studentItemView.setMaxScore(100);
        studentItemView.setQuestions(studentQuestionViewBuilder.withall().buildViewList(10));


        return this;


    }

    private List<StudentItemView> listbuilder(Integer count){

        studentItemViewList = IntStream.range(0,count).mapToObj(i->{
                StudentItemView studentsitemview = new StudentItemView();
            studentItemView.setUserFlagged(true);
            studentItemView.setTime(100 +i);
            studentItemView.setItemReference("itemReference" +i);
            studentItemView.setMaxScore(100 +i);
            studentItemView.setQuestions(studentQuestionViewBuilder.withall().buildViewList(10));

            return  studentsitemview;
        }).collect(Collectors.toList());
        return studentItemViewList;
    }



}
