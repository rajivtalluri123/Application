package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.entities.ScoreEntity;
import org.apache.commons.lang.RandomStringUtils;

import java.time.LocalDateTime;


/**
 * Created by srikanthk on 5/2/17.
 */
public class ScoreItemBuilder {

    private ScoreEntity scoreEntity = new ScoreEntity();


    public ScoreEntity build(){return scoreEntity;}


    public ScoreItemBuilder withAll(String questionReference, String itemReference){
        return this.withScoreReference()
                .withQuestionReference(questionReference)
                .withItemReference(itemReference)
                .withCreatedAt(LocalDateTime.now())
                .withUpdateAt(LocalDateTime.now());

    }

    public ScoreItemBuilder withScoreReference(){
        scoreEntity.setScoreReference(RandomStringUtils.randomAlphanumeric(10));
        return this;

    }


    public ScoreItemBuilder withQuestionReference(String questionReference){
       // scoreEntity.setQuestionReference(questionReference);
        return this;


    }

    public ScoreItemBuilder withItemReference(String itemReference){

        //scoreEntity.setItemReference(itemReference);
        return this;
    }


    public ScoreItemBuilder withCreatedAt(java.time.LocalDateTime createtime){

        scoreEntity.setCreatedDate(createtime);
        return this;
    }

    public ScoreItemBuilder withUpdateAt(java.time.LocalDateTime updateTime){

        scoreEntity.setCreatedDate(updateTime);
        return this;
    }




}
