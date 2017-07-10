package com.hmhco.api.grading.views.assignment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.hmheng.grading.utils.JsonCommons;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by nandipatim on 6/29/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("assignment-status")
public class StudentSessionStatus {

  @JsonProperty("event")
  private String event;

  @JsonProperty("session_id")
  private UUID sessionId;

  @JsonProperty("activity_id")
  private UUID activityRefId;

  @JsonProperty("user_id")
  private UUID userId;

  @JsonSerialize(using = JsonCommons.LocalDateTimeSerializerLearnosityStatusFormat.class)
  private LocalDateTime time;

}
