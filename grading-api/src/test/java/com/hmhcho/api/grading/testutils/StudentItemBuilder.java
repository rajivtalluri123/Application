package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.entities.StudentItemEntity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by srikanthk on 5/2/17.
 */
public class StudentItemBuilder {

    private StudentItemEntity studentItemEntity = new StudentItemEntity();



    public StudentItemEntity build(){return studentItemEntity;}


    public StudentItemBuilder withAll(UUID sessionId, String ItemReference){

        return this.withId()
                    .withItemReference(ItemReference)
                    .withSessionId(sessionId)
                    .withCreatedAt(LocalDateTime.now())
                    .withUpdateAt(LocalDateTime.now());


    }

    public StudentItemBuilder withId(){
        studentItemEntity.setRefId((long) Math.random());
        return this;
    }


     public StudentItemBuilder withSessionId(UUID sessionId){
         studentItemEntity.setSessionRefId(sessionId);
        return this;
     }

     public StudentItemBuilder withItemReference(String itemReference){

         studentItemEntity.setItemReference(itemReference);
         return this;
     }

    public StudentItemBuilder withCreatedAt(java.time.LocalDateTime createtime){

        studentItemEntity.setCreatedDate(createtime);
        return this;
    }

    public StudentItemBuilder withUpdateAt(java.time.LocalDateTime updateTime){

        studentItemEntity.setCreatedDate(updateTime);
        return this;
    }



}
