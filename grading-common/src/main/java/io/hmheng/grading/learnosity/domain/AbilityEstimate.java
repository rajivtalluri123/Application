package io.hmheng.grading.learnosity.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by pabonaj on 6/6/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("abilityestimate")
public class AbilityEstimate {

  @JsonProperty("ability_estimate")
  private Double abilityEstimate;
  @JsonProperty("standard_error")
  private Double standardError;
  @JsonProperty("method")
  private String method;

  public Double getAbilityEstimate() {
    return abilityEstimate;
  }

  public void setAbilityEstimate(Double abilityEstimate) {
    this.abilityEstimate = abilityEstimate;
  }

  public Double getStandardError() {
    return standardError;
  }

  public void setStandardError(Double standardError) {
    this.standardError = standardError;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  @Override
  public String toString() {
    return "AbilityEstimate{" +
        "abilityEstimate=" + abilityEstimate +
        ", standardError=" + standardError +
        ", method=" + method +
        '}';
  }
}
