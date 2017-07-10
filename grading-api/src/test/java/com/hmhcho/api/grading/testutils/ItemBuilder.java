package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.entities.ItemEntity;
import org.apache.commons.lang.RandomStringUtils;

import java.time.LocalDateTime;

/**
 * Created by srikanthk on 5/2/17.
 */
public class ItemBuilder {


    private ItemEntity itemEntity = new ItemEntity();


    public ItemEntity build(){return itemEntity;}


    public ItemBuilder withAll(){
        return this.withItemReference()
                .withCreatedAt(LocalDateTime.now())
                .withUpdateAt(LocalDateTime.now());
    }

    public ItemBuilder withItemReference(){
        itemEntity.setItemReference(RandomStringUtils.randomAlphabetic(10));
        return this;

    }


    public ItemBuilder withCreatedAt(java.time.LocalDateTime createtime){

        itemEntity.setCreatedDate(createtime);
        return this;
    }

    public ItemBuilder withUpdateAt(java.time.LocalDateTime updateTime){

        itemEntity.setCreatedDate(updateTime);
        return this;
    }



}
