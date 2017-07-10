package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.views.StudentQuestionView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by srikanthk on 5/2/17.
 */
public class StudentQuestionViewBuilder {

    private StudentScoreViewBuilder studentScoreViewBuilder = new StudentScoreViewBuilder();

    private StudentQuestionView studentQuestionView = new StudentQuestionView();

    private List<StudentQuestionView> studentQuestionViewList  = new ArrayList<>();

    public StudentQuestionView buildView(){return studentQuestionView;}

    public List<StudentQuestionView> buildViewList(Integer count ){ return  listbuilder(count);}



    public StudentQuestionViewBuilder withall(){
        studentQuestionView.setQuestionReference("questionReference");
        studentQuestionView.setActualResponse("actualResponse");

        return this;
    }


    private List<StudentQuestionView> listbuilder(Integer count){

        studentQuestionViewList = IntStream.range(0,count).mapToObj(i->{
            StudentQuestionView studentsquestionview = new StudentQuestionView();
            studentsquestionview.setQuestionReference("questionReference" +i);
            studentsquestionview.setActualResponse("actualresponses"+i);

            return      studentsquestionview;
                         }).collect(Collectors.toList());

        return studentQuestionViewList;
            }

        }


