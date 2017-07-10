package com.hmhco.api.grading.controller.utils;

import com.hmhco.api.grading.controller.exception.DataException;
import com.hmhco.api.grading.controller.exception.NotFoundException;
import com.hmhco.api.grading.controller.exception.UnsupportedVersionException;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerAdvice.class);

    @ExceptionHandler(value = { EmptyResultDataAccessException.class, EntityNotFoundException.class, NotFoundException.class })
    public ResponseEntity<?> handleNotFound(Exception ex) {
        return handleException(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<?> handleIllegalArgumentException(Exception ex) {
        return handleException(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class })
    public ResponseEntity<?> unsupportedMediaTypeException(Exception e) {
        logger.error("{}: {}", e.getClass().getCanonicalName(), e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
    }

    @ExceptionHandler(UnsupportedVersionException.class)
    public ResponseEntity<?> unsupportedVersionException(UnsupportedVersionException ex) {
        return handleException(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ObjectError>> argumentNotValid(MethodArgumentNotValidException e) {
        logger.error("{}: {}", e.getClass().getCanonicalName(), e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getBindingResult().getAllErrors());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> badRequest(HttpMessageNotReadableException e) {
        return handleException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInternalServerError(Exception e) {
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionInfo> handleException(Exception e, HttpStatus status) {
        logger.error("{}: {}", e.getClass().getCanonicalName(), e.getMessage(), e);
        return ResponseEntity.status(status).body(new ExceptionInfo(e));
    }
    
    @ExceptionHandler(DataException.class)
    public ResponseEntity<?> dataException(Exception ex) {
        return handleException(ex, HttpStatus.CONFLICT);
    }
}
