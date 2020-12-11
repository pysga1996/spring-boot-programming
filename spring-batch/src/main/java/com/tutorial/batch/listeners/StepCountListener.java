package com.tutorial.batch.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author thanhvt
 * @project spring-batch-demo
 */
@Component
@Primary
public class StepCountListener implements StepExecutionListener {

  private static final Logger logger = LoggerFactory.getLogger(StepCountListener.class);

  @Override
  public void beforeStep(StepExecution stepExecution) {
//        int count = stepExecution.getCommitCount();
////        logger.info("ItemCount: " + count);
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
//        int count = stepExecution.getCommitCount();
//        logger.info("ItemCount: " + count);
    return stepExecution.getExitStatus();
  }
}
