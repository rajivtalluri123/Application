package io.hmheng.grading.learnosity.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by pabonaj on 6/7/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("source")
public class Source {

  private String reference;

  @JsonProperty("organisation_id")
  private Integer organisationId;

  @JsonProperty("item_pool_id")
  private String itemPoolId;

  @Override
  public String toString() {
    return "Source{" +
        "reference='" + reference + '\'' +
        ", organisationId='" + organisationId + '\'' +
        ", itemPoolId=" + itemPoolId +
        '}';
  }
}
