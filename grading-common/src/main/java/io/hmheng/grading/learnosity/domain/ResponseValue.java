package io.hmheng.grading.learnosity.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import java.util.List;

/**
 * Created by pabonaj on 6/7/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("value")
@Relation(value = "value", collectionRelation = "values")
public class ResponseValue {

  private List<String> value;

  private String type;

  @JsonProperty("api_version")
  private String apiVersion;

  @JsonProperty("wordCount")
  private Integer wordCount;

  @Override
  public String toString() {
    return "ResponseValue{" +
        "value='" + value + '\'' +
        "wordCount='" + wordCount + '\'' +
        ", apiVersion='" + apiVersion + '\'' +
        '}';
  }

}
