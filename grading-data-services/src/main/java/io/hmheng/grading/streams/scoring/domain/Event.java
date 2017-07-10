package io.hmheng.grading.streams.scoring.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.hmheng.grading.utils.JsonCommons;
import io.hmheng.grading.util.enums.TestType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by nandipatim on 6/1/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

  private UUID activityId;

  private UUID eventId;

  private TestType testType;

  @JsonDeserialize(using = JsonCommons.LocalDateTimeDeserializer.class)
  private LocalDateTime availableDate;

  @JsonDeserialize(using = JsonCommons.LocalDateTimeDeserializer.class)
  private LocalDateTime dueDate;

  private boolean manualScoringRequired;
}
