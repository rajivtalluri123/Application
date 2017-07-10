package io.hmheng.grading.streams.grading.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.hmheng.grading.utils.JsonCommons;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Created by pabonaj on 6/6/17.
 */

@Data
@NoArgsConstructor
@JsonRootName("studentSession")
@Relation(value = "studentSession", collectionRelation = "studentSessions")
public class StudentActivitySession extends AbstractRequest {

  @NotNull
  @JsonProperty("sessionId")
  private UUID sessionRefId;

  @NotNull
  private UUID activityRefId;

  private UUID teacherAssignmentRefid;

  private UUID staffPersonalRefId;

  private Integer maxScore;

  private Integer maxTime;

  private Integer numQuestions;

  @NotNull
  private UUID studentPersonalRefId;

  private Integer numAttempted;

  private Integer score;

  private Integer sessionDuration;

  @NotNull
  private StudentAssignmentStatus status;

  @JsonSerialize(using = JsonCommons.LocalDateTimeSerializer.class)
  private LocalDateTime dateStarted;

  @JsonSerialize(using = JsonCommons.LocalDateTimeSerializer.class)
  private LocalDateTime dateCompleted;

  private String  userAgent;

  private List<ActivityItemScore> activityItems;

  private List<Items> items;

  private List<StudentItem> studentItems;

}

