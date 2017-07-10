package com.hmhco.api.grading.views;

import com.fasterxml.jackson.annotation.JsonRootName;
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
@JsonRootName("activity")
@Relation(value = "acivity", collectionRelation = "activities")
public class ActivityView extends AbstractView {

  @NotNull
  private UUID activityRefId;

  @NotNull
  private UUID teacherAssignmentRefId;

  @NotNull
  private UUID staffPersonalRefId;

  private Integer maxScore;

  private Integer maxTime;

  private Integer numQuestions;

  private List<ActivityItemScoreView> activityItemScores;

}
