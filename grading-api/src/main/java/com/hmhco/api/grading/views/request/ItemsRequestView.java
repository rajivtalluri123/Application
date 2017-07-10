package com.hmhco.api.grading.views.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.hmhco.api.grading.views.AbstractView;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

/**
 * Created by srikanthk on 4/26/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("item")
@Relation(value = "item", collectionRelation = "items")
public class ItemsRequestView extends AbstractView{

  @NotNull
  private String itemReference;

  private Integer organizationId;

  private String itemPoolId;

  private String type;

}
