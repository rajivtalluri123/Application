package com.hmhco.api.grading.mapper.entitymapper;

import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.ActivityEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.ActivityView;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Created by srikanthk on 4/28/17.
 */
@Component
public class ActivityEntityMapper implements SingleEntityMapper<ActivityEntity, ActivityView> {

    @Override
    public ActivityView convert( ActivityEntity entity){
        ActivityView activtityView = new ActivityView();
        BeanUtils.copyProperties(entity,activtityView);
        return activtityView;
}


    @Override
    public boolean supports(Class<? extends AbstractEntity> clazz){
        return ActivityEntity.class.equals(clazz);


    }

}
