package io.hmheng.grading.learnosity.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by pabonaj on 6/6/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("scoring")
public class Scoring {

  private Integer score;

  @JsonProperty("max_score")
  private Integer maxScore;

  private Object error;

  private String type;

  @JsonProperty("max_score_of_attempted")
  private Integer maxScoreOfAttempted;

  @JsonProperty("max_score_of_unmarked")
  private Integer maxScoreOfUnmarked;

  @Override
  public String toString() {
    return "Scoring{" +
        "score='" + score + '\'' +
        ", maxScore='" + maxScore + '\'' +
        ", error=" + error +
        ", maxScoreOfAttempted=" + maxScoreOfAttempted +
        ", maxScoreOfUnmarked=" + maxScoreOfUnmarked +
        '}';
  }
}
