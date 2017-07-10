package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.entities.StudentScoreEntity;
import org.apache.commons.lang.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by srikanthk on 5/2/17.
 */
public class StudentScoreBuilder {


    private StudentScoreEntity studentScoreEntity = new StudentScoreEntity();

    public StudentScoreEntity  build(){return studentScoreEntity;}



    public StudentScoreBuilder withAll(UUID sessionId, Integer studentItemRef, Integer studentQuestionRef){

        return this.withIdResponseID()
                    .withCreatedAt(LocalDateTime.now())
                    .withUpdateAt(LocalDateTime.now());

    }

   public  StudentScoreBuilder withIdResponseID(){

        studentScoreEntity.setResponseId(RandomStringUtils.randomAlphanumeric(10));
        return this;
    }

    public StudentScoreBuilder withCreatedAt(java.time.LocalDateTime createtime){

        studentScoreEntity.setCreatedDate(createtime);
        return this;
    }

    public StudentScoreBuilder withUpdateAt(java.time.LocalDateTime updateTime){

        studentScoreEntity.setCreatedDate(updateTime);
        return this;
    }



}
