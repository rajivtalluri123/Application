package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.views.ScoresView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Created by srikanthk on 5/2/17.
 */
public class ScoresViewBuilder {





    private   ScoresView scoresView = new ScoresView();

    private   List<ScoresView> ScoresViewList = new ArrayList<ScoresView>();

    public   ScoresView buildView(){return scoresView;}


    public  List<ScoresView> buildviewList(Integer count){return listbuilder(count); }



    private   List<ScoresView> listbuilder(Integer count){
        ScoresViewList = IntStream.range(0,count)
                .mapToObj(i->{
                    ScoresView scoresview = new ScoresView();
                    scoresview.setScoreReference("scorereference" + i);
                    scoresview.setAutomarkable(true);
                    scoresview.setCorrectResponse("correctresponse" +i);
                    return scoresview;
                }).collect(toList());

        return ScoresViewList;
    }

    public ScoresViewBuilder withAll(){

            scoresView.setCorrectResponse("correctResponse");
            scoresView.setAutomarkable(true);
            scoresView.setScoreReference("scoreReference");

            return this;
        }



    }


