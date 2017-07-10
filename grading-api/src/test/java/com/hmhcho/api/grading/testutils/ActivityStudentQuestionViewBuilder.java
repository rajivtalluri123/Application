package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.entities.readonly.ActivityStudentQuestionViewEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srikanthk on 5/16/17.
 */
public class ActivityStudentQuestionViewBuilder {

    List<ActivityStudentQuestionViewEntity> activityStudentQuestionViewList  = new ArrayList<>();

    ActivityStudentQuestionViewEntity activityStudentQuestionView = new ActivityStudentQuestionViewEntity();

    List<ActivityStudentQuestionViewEntity> getlist(int questioncount, int scorecount){


        for(int i =0; i < questioncount ; i++){
            ActivityStudentQuestionViewEntity studentQuestionGetView = new ActivityStudentQuestionViewEntity();
            ActivityStudentScoreViewBuilder activityStudentScoreViewBuilder  = new ActivityStudentScoreViewBuilder();
            studentQuestionGetView.setActualResponse("ActialResponse" +i);
            studentQuestionGetView.setResponses(activityStudentScoreViewBuilder.getList(scorecount));
            activityStudentQuestionViewList.add(studentQuestionGetView);
        }
        return activityStudentQuestionViewList;
    }

    ActivityStudentQuestionViewEntity getStudentQuestionGetView(){

        activityStudentQuestionView.setActualResponse("ActialResponse" );

        return activityStudentQuestionView;
    }



}
