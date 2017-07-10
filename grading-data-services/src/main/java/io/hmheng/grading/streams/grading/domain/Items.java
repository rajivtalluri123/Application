package io.hmheng.grading.streams.grading.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import java.util.List;

/**
 * Created by pabonaj on 6/6/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("item")
@Relation(value = "item", collectionRelation = "items")
public class Items extends ItemsRequest {

  private List<Questions> questions;

}