package com.hmhcho.api.grading.testutils;

import com.hmhco.api.grading.views.QuestionsView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by srikanthk on 5/2/17.
 */
public class QuestionViewBuilder {


    private QuestionsView questionsview = new QuestionsView();

    private List<QuestionsView> questionsViewList = new ArrayList<>();

 private ScoresViewBuilder scoresViewBuilder = new ScoresViewBuilder();

    public QuestionsView buildView(){return questionsview;}


    public List<QuestionsView> buildviewList(Integer count){return listbuilder(count); }


    public QuestionViewBuilder withAll(){



        questionsview.setQuestionType("questionType");
        questionsview.setQuestionReference("questionReference");
        questionsview.setRubricReference("rubicreference");
        questionsview.setScores(scoresViewBuilder.buildviewList(1));
        return this;

    }


    private List<QuestionsView> listbuilder(Integer count) {

        questionsViewList = IntStream.range(0, count).mapToObj(i -> {
            QuestionsView questionview = new QuestionsView();
            questionview.setRubricReference("rubricreference" + i);
            questionview.setQuestionReference("questionreference" + i);
            questionview.setQuestionType("questionType" + i);
            questionview.setScores(scoresViewBuilder.buildviewList(1));
            return questionview;
        }).collect(Collectors.toList());

        return questionsViewList;
    }

}
