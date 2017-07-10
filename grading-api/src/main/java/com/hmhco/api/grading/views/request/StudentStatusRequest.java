package com.hmhco.api.grading.views.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.hmheng.grading.utils.StudentAssignmentStatus;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

/**
 * Created by nandipatim on 5/17/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("studentStatus")
@Relation(value = "studentStatus", collectionRelation = "studentStatuses")
public class StudentStatusRequest {

  @NotNull
  private StudentAssignmentStatus status;
}
