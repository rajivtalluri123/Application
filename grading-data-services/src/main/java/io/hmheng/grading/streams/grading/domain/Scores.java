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
@JsonRootName("score")
@Relation(value = "score", collectionRelation = "scores")
public class Scores extends AbstractRequest {

  @NotNull
  private String scoreReference;

  private String title;

  private String description;

  private boolean automarkable;

  private String correctResponse;
}