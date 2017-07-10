package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.views.StudentScoreView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Created by srikanthk on 5/2/17.
 */
public class StudentScoreViewBuilder {

    private StudentScoreView studentscoreview = new StudentScoreView();

    private List<StudentScoreView> studentScoreViewList = new ArrayList<>();


    public StudentScoreView buildView(){return studentscoreview;}



    public List<StudentScoreView> buildList(Integer count){return listbuilder(count);}



    public StudentScoreViewBuilder withAll(){

        studentscoreview.setScoreReference("scorereference");
        studentscoreview.setStaffPersonalRefId(UUID.randomUUID());
        studentscoreview.setScore(100);
        studentscoreview.setWeight(100);
        studentscoreview.setMaxScore(100);
        studentscoreview.setValue("value");
        studentscoreview.setAttempted(true);

        return this;

    }

    private  List<StudentScoreView> listbuilder(Integer count){
        studentScoreViewList = IntStream.range(0,count).mapToObj(i->{
            StudentScoreView studentsscoreview = new StudentScoreView();
            studentsscoreview.setScoreReference("scorereference"+i);
            studentsscoreview.setStaffPersonalRefId(UUID.randomUUID());
            studentsscoreview.setScore(100+i);
            studentsscoreview.setWeight(100 +i);
            studentsscoreview.setMaxScore(100+i);
            studentsscoreview.setValue("value"+i);
            studentsscoreview.setAttempted(true);
            return studentsscoreview;
        }).collect(toList());

        return studentScoreViewList;

    }



}
