package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.entities.readonly.ActivityStudentItemViewEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srikanthk on 5/16/17.
 */
public class ActivityStudentItemViewBuilder {


    private ActivityStudentItemViewEntity activityStudentItemView = new ActivityStudentItemViewEntity();

    private List<ActivityStudentItemViewEntity> activityStudentItemViewList = new ArrayList<>();


    public ActivityStudentItemViewEntity getActivityStudentItemViewList( int questioncount, int
            scorecount){

            ActivityStudentQuestionViewBuilder activityStudentQuestionViewBuilder = new
                    ActivityStudentQuestionViewBuilder();

            activityStudentItemView.setItemReference("item" );

            activityStudentItemView.setQuestions(activityStudentQuestionViewBuilder.getlist(questioncount,scorecount));

        return activityStudentItemView;
    }

}
