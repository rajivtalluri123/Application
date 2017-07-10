package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.views.ItemsView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by srikanthk on 5/2/17.
 */
public class ItemViewBuilder {

    private ItemsView itemview = new ItemsView();

    private List<ItemsView> itemsViewList = new ArrayList<>();


    private QuestionViewBuilder questionViewBuilder = new QuestionViewBuilder();

    public ItemsView buildView(){return itemview;}


    public List<ItemsView> buildviewList(Integer count){return listbuilder(count); }


    public ItemViewBuilder withAll(){

        itemview.setType("type");
        itemview.setItemPoolId("itemPoolId");
        itemview.setOrganizationId(10000);
        itemview.setItemReference("ItemReference");
        itemview.setQuestions(questionViewBuilder.buildviewList(1));
        return this;

    }



    private List<ItemsView> listbuilder(Integer count){

        itemsViewList = IntStream.range(0,count).mapToObj(i->{
            ItemsView itemview = new ItemsView();
            itemview.setItemReference("itemReference"+i);
            itemview.setItemPoolId("itemPoolid"+i);
            itemview.setItemReference("itemreference"+i);
            itemview.setOrganizationId(1000 + i );
            itemview.setQuestions(questionViewBuilder.buildviewList(1));
            return itemview;
        }).collect(Collectors.toList());

        return itemsViewList;

    }





}
