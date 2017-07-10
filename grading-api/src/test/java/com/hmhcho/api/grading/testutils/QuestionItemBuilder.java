package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.entities.QuestionEntity;
import org.apache.commons.lang.RandomStringUtils;

import java.time.LocalDateTime;

/**
 * Created by srikanthk on 5/2/17.
 */
public class QuestionItemBuilder {


    private QuestionEntity questionEntity = new QuestionEntity();

    public QuestionEntity build() {return questionEntity;}

    QuestionItemBuilder withQuestionReference(){
        questionEntity.setQuestionReference(RandomStringUtils.randomAlphanumeric(10));
        return this;
    }


    public QuestionItemBuilder withAll(String questionReference, String itemReference){
        return this.withQuestionReference()
                .withItemReference(itemReference)
                .withCreatedAt(LocalDateTime.now())
                .withUpdateAt(LocalDateTime.now());

    }


    QuestionItemBuilder withItemReference(String itemReference){
        //questionEntity.setItemReference(itemReference);
        return this;
    }

    public QuestionItemBuilder withCreatedAt(java.time.LocalDateTime createtime){

        questionEntity.setCreatedDate(createtime);
        return this;
    }

    public QuestionItemBuilder withUpdateAt(java.time.LocalDateTime updateTime){

        questionEntity.setCreatedDate(updateTime);
        return this;
    }




}
