package io.hmheng.grading.learnosity.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Item {

  private String reference;

  private Source source;

  private Integer time;

  @JsonProperty("response_ids")
  private List<String> responseIds;

  @JsonProperty("user_flagged")
  private boolean userFlagged;

  private Scoring scoring;

  @Override
  public String toString() {
    return "Item{" +
        "reference='" + reference + '\'' +
        ", source='" + source + '\'' +
        ", time=" + time +
        ", responseIds=" + responseIds +
        ", userFlagged=" + userFlagged +
        ", scoring=" + scoring +
        '}';
  }
}
