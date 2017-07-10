package com.hmhco.api.grading.controller.exception;

/**
 * Created by nandipatim on 6/6/17.
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class PreConditionRequiredException  extends RuntimeException  {

  private static final long serialVersionUID = 1L;

  public PreConditionRequiredException(String message) {
    super(message);
  }
}
