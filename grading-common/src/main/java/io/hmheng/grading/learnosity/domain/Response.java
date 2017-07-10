package io.hmheng.grading.learnosity.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by pabonaj on 6/8/17.
 */
@Data
@NoArgsConstructor
@JsonRootName("response")
public class Response {

  private List<ResponseValue> value;

  @Override
  public String toString() {
    return "Response{" +
        "value='" + value + '\'' +
        '}';
  }
}
