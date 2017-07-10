package com.hmhco.api.grading.views;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.hmhco.api.grading.views.request.ItemsRequestView;
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
@Relation(value = "item", collectionRelation = "item")
public class ItemsView  extends ItemsRequestView{

  private List<QuestionsView> questions;

}
