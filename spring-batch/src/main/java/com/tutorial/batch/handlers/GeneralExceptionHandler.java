package com.tutorial.batch.handlers;

import org.springframework.batch.core.StartLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author thanhvt
 * @project spring-batch-demo
 */

@ControllerAdvice
public class GeneralExceptionHandler {

  @ExceptionHandler(value = StartLimitExceededException.class)
  public ResponseEntity<String> exception(StartLimitExceededException exception) {
    exception.printStackTrace();
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
