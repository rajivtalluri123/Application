package io.hmheng.grading.streams.grading.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by pabonaj on 6/6/17.
 */
public class AbstractRequest {

  @JsonIgnore
  private String version;

  public String getVersion(){return version;}

  public void setVersion(String version){this.version = version;}
}

