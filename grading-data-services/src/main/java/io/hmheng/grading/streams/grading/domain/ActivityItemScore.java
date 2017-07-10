package io.hmheng.grading.streams.grading.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotNull;

/**
 * Created by pabonaj on 6/6/17.
 */

@Data
@NoArgsConstructor
@JsonRootName("activityItemScore")
@Relation(value = "activityItemScore", collectionRelation = "activityItemScores")
public class ActivityItemScore extends AbstractRequest {


  @NotNull
  private String itemReference;

  @NotNull
  private String scoreReference;

  @NotNull
  private String questionReference;

  private Integer maxScore;

  private Integer weight;
}
