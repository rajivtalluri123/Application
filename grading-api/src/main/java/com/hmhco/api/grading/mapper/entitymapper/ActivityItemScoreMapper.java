package com.hmhco.api.grading.mapper.entitymapper;

import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.ActivityItemScoreEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.ActivityItemScoreView;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Created by srikanthk on 4/28/17.
 */
@Component
public class ActivityItemScoreMapper implements SingleEntityMapper<ActivityItemScoreEntity, ActivityItemScoreView> {

    @Override
    public ActivityItemScoreView convert( ActivityItemScoreEntity entity){

        ActivityItemScoreView activityItemScoreView = new ActivityItemScoreView();
        BeanUtils.copyProperties(entity,activityItemScoreView);
        return activityItemScoreView;
    }


    @Override
    public boolean supports(Class<? extends AbstractEntity> clazz){
        return ActivityItemScoreEntity.class.equals(clazz);

    }

}
