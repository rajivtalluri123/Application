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
@JsonRootName("item")
@Relation(value = "item", collectionRelation = "items")
public class ItemsRequest extends AbstractRequest {

  @NotNull
  private String itemReference;

  private Integer organizationId;

  private String itemPoolId;

  private String type;

}
