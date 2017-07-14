package io.hmheng.grading.streams.grading.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * Created by pabonaj on 6/6/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("studentQuestion")
@Relation(value = "studentQuestion", collectionRelation = "studentQuestions")
public class StudentQuestion extends AbstractRequest {

  @Null
  private Long refId;

  @NotNull
  private  String questionReference;

  private Object actualResponse;

  private String responseId;

  private List<StudentScore> responses;

}
