package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.entities.StudentQuestionEntity;

import java.time.LocalDateTime;

/**
 * Created by srikanthk on 5/2/17.
 */
public class StudentQuestionItemBuilder {

    StudentQuestionEntity studentQuestionEntity = new StudentQuestionEntity();

    public StudentQuestionEntity build(){return studentQuestionEntity;}


    public StudentQuestionItemBuilder withAll(String questionReference, Long studentItemRefid){

        return this.withId()
                    .withQuestionReference(questionReference)
                    .withCreatedAt(LocalDateTime.now())
                    .withUpdateAt(LocalDateTime.now());
    }


    public StudentQuestionItemBuilder withId(){

        studentQuestionEntity.setRefId((long)Math.random());
        return this;
    }

    public StudentQuestionItemBuilder withQuestionReference(String  questionReference){

        studentQuestionEntity.setQuestionReference(questionReference);
        return this;
    }

    public StudentQuestionItemBuilder withCreatedAt(java.time.LocalDateTime createtime){

        studentQuestionEntity.setCreatedDate(createtime);
        return this;
    }

    public StudentQuestionItemBuilder withUpdateAt(java.time.LocalDateTime updateTime){

        studentQuestionEntity.setCreatedDate(updateTime);
        return this;
    }





}
