package com.hmhco.api.grading.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by somashekara on 4/24/16.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalArgumentException extends RuntimeException {

    private static final long serialVersionUID = -3121508631111536854L;
    private String message;

    public IllegalArgumentException() { }

    public IllegalArgumentException(String message) { this.message = message; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

}
