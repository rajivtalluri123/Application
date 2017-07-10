package com.hmhco.api.grading.mapper.viewmapper;

import com.hmhco.api.grading.entities.ActivityItemScoreEntity;
import com.hmhco.api.grading.views.AbstractView;
import com.hmhco.api.grading.views.ActivityItemScoreView;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Created by srikanthk on 4/28/17.
 */
@Component
public class ActivityItemScoreViewMapper implements SingleViewMapper<ActivityItemScoreView , ActivityItemScoreEntity> {

    @Override
    public ActivityItemScoreEntity convert( ActivityItemScoreView view){

        ActivityItemScoreEntity activityItemScoreEntity = new ActivityItemScoreEntity();
        BeanUtils.copyProperties(view,activityItemScoreEntity);
        return activityItemScoreEntity;
    }

    @Override
    public boolean supports(Class<? extends AbstractView> clazz){
        return ActivityItemScoreView.class.equals(clazz);


    }
}
