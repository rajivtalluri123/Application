package com.hmhco.api.grading.views.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.hmhco.api.grading.views.AbstractView;
import com.hmhco.api.grading.views.ActivityItemScoreView;
import com.hmhco.api.grading.views.ScoresView;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

/**
 * Created by nandipatim on 5/8/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("response")
@Relation(value = "response", collectionRelation = "responses")
@JsonIgnoreProperties({"item.questions","question.scores","studentItem.questions","studentQuestion.responses"})
public class ResponseView extends AbstractView {

  @Null
  private String responseId;

  @NotNull
  private String scoreReference;

  private UUID staffPersonalRefId;

  private Boolean attempted;

  private String value;

  @NotNull
  private Integer weight;

  @NotNull
  private Integer maxScore;

  private Integer score;

  //Only Want to update ITEM
  private ItemsRequestView item;

  //Only Want to update Question
  private QuestionsRequestView question;

  //Only Want to update Score or insert
  private ScoresView scores;

  @NotNull
  private StudentItemRequestView studentItem;

  @NotNull
  private StudentQuestionRequestView studentQuestion;

}
