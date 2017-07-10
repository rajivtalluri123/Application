package io.hmheng.grading.streams.grading.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by pabonaj on 6/6/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("studentScore")
@Relation(value = "studentScore", collectionRelation = "studentScores")
public class StudentScore extends AbstractRequest {

  @NotNull
  private String responseId;

  private String scoreReference;

  private UUID staffPersonalRefId;

  private Boolean attempted;

  private Object value;

  private Integer weight;

  private Integer maxScore;

  private Integer score;
}
