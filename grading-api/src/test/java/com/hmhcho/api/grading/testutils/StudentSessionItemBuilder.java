package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.entities.StudentSessionEntity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by srikanthk on 5/2/17.
 */
public class StudentSessionItemBuilder {

    private StudentSessionEntity studentSessionEntity = new StudentSessionEntity();


    public StudentSessionEntity build(){return studentSessionEntity;}


    public StudentSessionItemBuilder withAll(UUID sessionId, UUID activityRefId){

        return this.withActivityRefId(activityRefId)
                    .withSessionID(sessionId)
                    .withstudentPersonalRefId(UUID.randomUUID())
                    .withCreatedAt(LocalDateTime.now())
                    .withUpdateAt(LocalDateTime.now());

    }

    public StudentSessionItemBuilder withSessionID(UUID studentSessionId){

        studentSessionEntity.setSessionRefId(studentSessionId);
        return this;
    }

    public StudentSessionItemBuilder withActivityRefId(UUID activityRefId){
        studentSessionEntity.setActivityRefId(activityRefId);
        return this;
    }

    public StudentSessionItemBuilder withstudentPersonalRefId(UUID studentPersonalRefID){
        studentSessionEntity.setStudentPersonalRefId(studentPersonalRefID);
        return this;
    }






    public StudentSessionItemBuilder withCreatedAt(java.time.LocalDateTime createtime){

        studentSessionEntity.setCreatedDate(createtime);
        return this;
    }

    public StudentSessionItemBuilder withUpdateAt(java.time.LocalDateTime updateTime){

        studentSessionEntity.setCreatedDate(updateTime);
        return this;
    }


}
