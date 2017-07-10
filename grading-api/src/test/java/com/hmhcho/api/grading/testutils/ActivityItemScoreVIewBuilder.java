package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.views.ActivityItemScoreView;
import com.hmhco.api.grading.views.ItemsView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by srikanthk on 5/2/17.
 */
public class ActivityItemScoreVIewBuilder {

    private ActivityItemScoreView activityItemScoreView = new ActivityItemScoreView();

    private List<ActivityItemScoreView> activityItemScoreViewList = new ArrayList<>();

    private ItemViewBuilder itemViewBuilder = new ItemViewBuilder();

    public ActivityItemScoreView buildView(){return activityItemScoreView; }

    public List<ActivityItemScoreView> buildList(Integer count){return listBuilder(count);}


    public ActivityItemScoreVIewBuilder withAll(){

        activityItemScoreView.setWeight(100);
        activityItemScoreView.setScoreReference("scorereference");
        activityItemScoreView.setMaxScore(100);
        activityItemScoreView.setItemReference("itemreference");
     //   activityItemScoreView.setItems(itemViewBuilder.withAll().buildView());
        return this;

    }

    private List<ActivityItemScoreView> listBuilder(Integer count){

        activityItemScoreViewList = IntStream.range(0,count).mapToObj(
                i->{ ActivityItemScoreView activityItemScoreView = new ActivityItemScoreView();
                    activityItemScoreView.setItemReference("itemReference"+i);
                    activityItemScoreView.setMaxScore(1000+i);
                    activityItemScoreView.setWeight(100+i);
                    activityItemScoreView.setScoreReference("scoreReference"+i);
       //             activityItemScoreView.setItems(itemViewBuilder.withAll().buildView());
                    return activityItemScoreView;
                }
        ).collect(Collectors.toList());

        return activityItemScoreViewList;
    }

}
