package com.tutorial.batch.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.stereotype.Component;

/**
 * @author thanhvt
 * @project spring-batch-demo
 */

@Component
public class StepExceptionHandler implements ExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(StepExceptionHandler.class);

  @Override
  public void handleException(RepeatContext repeatContext, Throwable throwable) throws Throwable {
    logger.info(throwable.getMessage());
    throw new Exception(throwable);
  }
}
