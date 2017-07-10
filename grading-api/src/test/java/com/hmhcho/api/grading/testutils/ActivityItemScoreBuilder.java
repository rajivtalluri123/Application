package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.entities.ActivityItemScoreEntity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by srikanthk on 5/2/17.
 */
public class ActivityItemScoreBuilder {



    private ActivityItemScoreEntity activityItemScoreEntity = new ActivityItemScoreEntity();

    public ActivityItemScoreEntity build(){return activityItemScoreEntity;}

    public ActivityItemScoreBuilder withAll(UUID activityRefID, String scoreReference, String itemReference){

        return this.withRefID()
                    .withActivityRefID(activityRefID)
                    .withitemReference(itemReference)
                    .withScoreReference(scoreReference)
                    .withCreatedAt(LocalDateTime.now())
                    .withUpdateAt(LocalDateTime.now());

    }


    public ActivityItemScoreBuilder withRefID(){

        activityItemScoreEntity.setRefId((long)Math.random());
        return this;
    }

    public ActivityItemScoreBuilder withActivityRefID(UUID activityRefID){

        activityItemScoreEntity.setActivityRefId(activityRefID);
        return this;
    }

    public ActivityItemScoreBuilder withitemReference(String itemreference){

        activityItemScoreEntity.setItemReference(itemreference);
        return this;
    }


    public ActivityItemScoreBuilder withScoreReference(String scoreReference){
        activityItemScoreEntity.setScoreReference(scoreReference);
        return this;

    }

    public ActivityItemScoreBuilder withCreatedAt(java.time.LocalDateTime createtime){

        activityItemScoreEntity.setCreatedDate(createtime);
        return this;
    }

    public ActivityItemScoreBuilder withUpdateAt(java.time.LocalDateTime updateTime){

        activityItemScoreEntity.setCreatedDate(updateTime);
        return this;
    }



}
