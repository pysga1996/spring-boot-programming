package com.tutorial.batch.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.stereotype.Component;

/**
 * @author thanhvt
 * @project spring-batch-demo
 */
@Component
public class SkipLimiter implements SkipPolicy {

  private static final Logger logger = LoggerFactory.getLogger(SkipLimiter.class);

  @Override
  public boolean shouldSkip(Throwable throwable, int i) throws SkipLimitExceededException {
    if (i > 3) {
      logger.info("Exceed skip limit");
      return false;
    } else {
      return true;
    }
  }
}
