package com.hmhco.api.grading.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by srikanthk on 5/2/17.
 */

@ResponseStatus(code= HttpStatus.NO_CONTENT)
public class InvalidInputException  extends  RuntimeException{


    public InvalidInputException(String message){

        super(message);
    }


    public InvalidInputException(String message , Throwable cause){

        super(message,cause);
    }

}
