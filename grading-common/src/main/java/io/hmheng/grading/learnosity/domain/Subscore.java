package io.hmheng.grading.learnosity.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by pabonaj on 6/6/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("subscore")
public class Subscore {

  private String id;

  private String title;

  @JsonProperty("num_questions")
  private Integer numQuestions;

  @JsonProperty("num_attempted")
  private Integer numAttempted;

  @JsonProperty("num_unmarked")
  private Integer numUnmarked;

  private Integer score;

  @JsonProperty("max_score")
  private Integer maxScore;

  @JsonProperty("attempted_max_score")
  Integer attemptedMaxScore;

  @JsonProperty("unmarked_max_score")
  private Integer unmarkedMaxScore;

  private List<String> items;

  @JsonProperty("ability_estimate")
  private AbilityEstimate abilityEstimate;

  @Override
  public String toString() {
    return "Subscore{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", numQuestions=" + numQuestions +
        ", numAttempted=" + numAttempted +
        ", numUnmarked=" + numUnmarked +
        ", score=" + score +
        ", maxScore=" + maxScore +
        ", attemptedMaxScore=" + attemptedMaxScore +
        ", unmarkedMaxScore=" + unmarkedMaxScore +
        ", items=" + items +
        ", abilityEstimate=" + abilityEstimate +
        '}';
  }
}

