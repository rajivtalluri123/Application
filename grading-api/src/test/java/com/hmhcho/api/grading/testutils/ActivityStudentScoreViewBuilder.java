package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.entities.readonly.ActivityStudentScoreViewEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srikanthk on 5/16/17.
 */
public class ActivityStudentScoreViewBuilder {


    ActivityStudentScoreViewEntity activityStudentScoreView = new ActivityStudentScoreViewEntity();

    List<ActivityStudentScoreViewEntity> StudentScoreGetViewList = new ArrayList<>();


    public List<ActivityStudentScoreViewEntity> getList(int size){


        for(int i =0 ;i <size; i++){
            ActivityStudentScoreViewEntity studentScoreGetView = new ActivityStudentScoreViewEntity();


            studentScoreGetView.setAutomarkable(true);
            studentScoreGetView.setCorrectResponse("correctresponse"+ i);
            StudentScoreGetViewList.add(studentScoreGetView);

        }

        return StudentScoreGetViewList;
    }

    public ActivityStudentScoreViewEntity getScoreView(){


        activityStudentScoreView.setAutomarkable(true);
        activityStudentScoreView.setCorrectResponse("correctresponse");

        return activityStudentScoreView;
    }









}
