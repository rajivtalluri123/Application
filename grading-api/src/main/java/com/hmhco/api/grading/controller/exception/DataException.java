package com.hmhco.api.grading.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
*
created by jayachandranj on Apr 18, 2016
*
*/
@ResponseStatus(HttpStatus.CONFLICT)
public class DataException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    public DataException(String message) {
        super(message);
    }

    

}
