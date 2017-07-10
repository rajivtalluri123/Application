package com.hmhco.api.grading.mapper.viewmapper;

import com.hmhco.api.grading.entities.ActivityEntity;
import com.hmhco.api.grading.views.AbstractView;
import com.hmhco.api.grading.views.ActivityView;
import io.hmheng.grading.utils.BeanPropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by srikanthk on 4/30/17.
 */
@Component
public class ActivityViewMapper implements SingleViewMapper<ActivityView, ActivityEntity> {

    @Autowired
    private ActivityItemScoreViewMapper activityItemScoreViewMapper;


    @Override
    public ActivityEntity convert(ActivityView view) {
        ActivityEntity activtyEntity = new ActivityEntity();
        BeanPropertyUtils.copyPropertiesWithOnlyPopulated(view, activtyEntity);
        if(view.getActivityItemScores() != null) {
            activtyEntity.setActivityItemScores(activityItemScoreViewMapper.convert(view.getActivityItemScores()));
        }
        return activtyEntity;
    }

    @Override
    public boolean supports(Class<? extends AbstractView> clazz) {

        return ActivityView.class.equals(clazz);
    }
}
