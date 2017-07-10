package io.hmheng.grading.streams.grading.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by pabonaj on 6/6/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("studentItem")
@Relation(value = "studentItem", collectionRelation = "studentItems")
public class StudentItem extends AbstractRequest {

  private Long refId;

  @NotNull
  private String itemReference;

  @NotNull
  private Integer time;

  private Integer maxScore;

  private Boolean userFlagged;

  private List<StudentQuestion> questions;

}
