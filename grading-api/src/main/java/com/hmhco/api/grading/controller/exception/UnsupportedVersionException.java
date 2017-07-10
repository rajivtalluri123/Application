package com.hmhco.api.grading.controller.exception;

/**
 * Created by nandipatim on 9/30/15.
 */
public class UnsupportedVersionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnsupportedVersionException(){}

    public UnsupportedVersionException(String msg){super(msg);}
}
