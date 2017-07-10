package io.hmheng.grading.learnosity.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by pabonaj on 6/8/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("responses")
public class Responses {

  @JsonProperty("response_id")
  private String responseId;

  @JsonProperty("question_type")
  private String questionType;

  @JsonProperty("dt_score_updated")
  private String scoreUpdatedDate;

  @JsonProperty("automarkable")
  private boolean isAutomarkable;

  @JsonProperty("attempted")
  private boolean isAttempted;

  private Integer score;

  @JsonProperty("max_score")
  private Integer maxScore;

  @JsonProperty("question_reference")
  private String questionReference;

  @JsonProperty("item_reference")
  private String itemReference;

  @JsonProperty("manual_score")
  private Integer manualScore;

  @JsonProperty("max_manual_score")
  private Integer maxManualScore;

  @JsonProperty("response")
  private Map<String,Object> response;

  @Override
  public String toString() {
    return "Responses{" +
        "responseId='" + responseId + '\'' +
        ", questionType='" + questionType + '\'' +
        ", scoreUpdatedDate=" + scoreUpdatedDate +
        ", automarkable=" + isAutomarkable +
        ", attempted=" + isAttempted +
        ", score=" + score +
        ", maxScore=" + maxScore +
        ", questionReference=" + questionReference +
        ", itemReference=" + itemReference +
        ", manualScore=" + manualScore +
        ", maxManualScore=" + maxManualScore +
        ", response=" + response +
        '}';
  }
}
