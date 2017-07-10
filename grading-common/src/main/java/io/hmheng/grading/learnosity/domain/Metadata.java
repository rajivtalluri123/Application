package io.hmheng.grading.learnosity.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by pabonaj on 6/8/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("metadata")
public class Metadata {

  @JsonProperty("items_api_version")
  private String itemsApiVersion;

  @JsonProperty("max_time")
  private Integer maxTime;

  @JsonProperty("user_agent")
  private String userAgent;

  @JsonProperty("activity_template_id")
  private String activityTemplateId;

  @Override
  public String toString() {
    return "Metadata{" +
        "itemsApiVersion='" + itemsApiVersion + '\'' +
        ", maxTime='" + maxTime + '\'' +
        ", userAgent=" + userAgent +
        ", activityTemplateId=" + activityTemplateId +
        '}';
  }
}
