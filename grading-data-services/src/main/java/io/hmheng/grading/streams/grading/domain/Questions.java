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
@JsonRootName("question")
@Relation(value = "question", collectionRelation = "questions")
public class Questions extends AbstractRequest {

  @NotNull
  private String questionReference;

  private String rubricReference;

  private String questionType;

  private boolean automarkable;

  List<Scores> scores;

}