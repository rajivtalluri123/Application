package com.hmhco.api.grading.views.getresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.hmhco.api.grading.views.AbstractView;
import com.hmhco.api.grading.views.ActivityView;
import io.hmheng.grading.utils.StudentAssignmentStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

/**
 * Created by nandipatim on 5/1/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("studentSession")
@Relation(value = "studentSession", collectionRelation = "studentSessions")
public class StudentSessionGetView extends AbstractView{

  @NotNull
  @JsonProperty("sessionId")
  private UUID sessionRefId;

  @NotNull
  private UUID studentPersonalRefId;

  private Integer numAttempted;

  private Integer score;

  private Integer sessionDuration;

  @NotNull
  private StudentAssignmentStatus status;

  private LocalDateTime dateStarted;

  private LocalDateTime dateCompleted;

  //TODO Dependent on Review with UI will change the name to activity if in case has to be changed to Activity.
  @NotNull
  private ActivityView assignment;

  List<StudentItemGetView> items;

}
