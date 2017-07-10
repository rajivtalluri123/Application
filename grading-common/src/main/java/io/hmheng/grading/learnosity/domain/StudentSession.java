package io.hmheng.grading.learnosity.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.hmheng.grading.utils.JsonCommons;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.UUID;

/**
 * Created by nandipatim on 5/31/17.
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentSession {

  @JsonProperty("activity_id")
  private UUID activityId;

  @JsonProperty("max_score")
  private Integer maxScore;

  private Integer score;

  @JsonProperty("session_id")
  private UUID sessionId;

  private String status;

  private List<Subscore> subscores;

  @JsonProperty("user_id")
  private UUID userId;

  @JsonProperty("dt_started")
  @JsonDeserialize(using = JsonCommons.LocalDateTimeDeserializerISOFormat.class)
  @JsonSerialize(using = JsonCommons.LocalDateTimeSerializerISOFormat.class)
  private LocalDateTime startDate;

  @JsonProperty("dt_completed")
  @JsonDeserialize(using = JsonCommons.LocalDateTimeDeserializerISOFormat.class)
  @JsonSerialize(using = JsonCommons.LocalDateTimeSerializerISOFormat.class)
  private LocalDateTime completedDate;

  @JsonProperty("num_attempted")
  private Integer numAttempted;

  @JsonProperty("num_questions")
  private Integer numQuestions;

  @JsonProperty("session_duration")
  private Integer sessionDuration;

  private List<Responses> responses;

  private List<Item> items;

  private Metadata metadata;

  private Boolean isGradingRequired;

  @Override
  public String toString() {
    return "StudentSession{" +
        "activityId=" + activityId +
        ", maxScore=" + maxScore +
        ", score=" + score +
        ", sessionId=" + sessionId +
        ", status='" + status + '\'' +
        ", subscores=" + subscores +
        ", userId=" + userId +
        ", startDate=" + startDate +
        ", completedDate=" + completedDate +
        ", numAttempted=" + numAttempted +
        ", numQuestions=" + numQuestions +
        ", sessionDuration=" + sessionDuration +
        ", responses=" + responses +
        ", items=" + items +
        ", metadata=" + metadata +
        ", isGradingRequired=" + isGradingRequired +
        '}';
  }
}
