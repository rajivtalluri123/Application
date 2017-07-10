package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.entities.ActivityEntity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by srikanthk on 5/2/17.
 */
public class ActivityItemBuilder {


    private ActivityEntity activityEntity = new ActivityEntity();

    public ActivityEntity build(){return activityEntity;}


    public ActivityItemBuilder withAll(UUID activityRefID){

        return this.withActivityRefID(activityRefID)
                    .withCreatedAt(LocalDateTime.now())
                    .withUpdateAt(LocalDateTime.now());
    }

    public ActivityItemBuilder withActivityRefID(UUID activityRefID){
        activityEntity.setActivityRefId(activityRefID);
        return this;
    }


    public ActivityItemBuilder withCreatedAt(java.time.LocalDateTime createtime){

        activityEntity.setCreatedDate(createtime);
        return this;
    }

    public ActivityItemBuilder withUpdateAt(java.time.LocalDateTime updateTime){

        activityEntity.setCreatedDate(updateTime);
        return this;
    }

}
