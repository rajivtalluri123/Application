package com.hmhco.api.grading.views;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.hmheng.grading.utils.StudentAssignmentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by pabonaj on 5/23/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("studentSession")
public class SessionStatusView {

  @NotNull
  private UUID sessionId;

  @NotNull
  private StudentAssignmentStatus status;

  @NotNull
  private UUID studentPersonalRefId;

  private LocalDateTime dateStarted;

  private LocalDateTime dateCompleted;

  private Integer numAttempted;

  private Integer score;

  private Integer sessionDuration;

}
