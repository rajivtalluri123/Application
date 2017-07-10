package com.hmhco.api.grading.controller.utils;

public class ExceptionInfo {

    private String exception;
    private String message;
    
    public ExceptionInfo(Exception e) {
        this.exception = e.getClass().getName();
        this.message = e.getMessage();
    }

    public ExceptionInfo(String exception, String message) {
        this.exception = exception;
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
