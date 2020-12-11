package com.tutorial.batch.listeners;

import com.tutorial.batch.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StartLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author thanhvt
 * @project spring-batch-demo
 */

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

  private static final Logger logger = LoggerFactory
      .getLogger(JobCompletionNotificationListener.class);

  private final JdbcTemplate jdbcTemplate;

  private final JdbcTemplate batchJdbcTemplate;

  @Autowired
  public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate,
      @Qualifier("batchJdbcTemplate") JdbcTemplate batchJdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.batchJdbcTemplate = batchJdbcTemplate;
  }

  @Override
  public void beforeJob(JobExecution jobExecution) {
//        jobExecution.getStepExecutions().

  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      logger.info("!!! JOB FINISHED! Time to verify the results");

      jdbcTemplate.query("SELECT first_name, last_name FROM people",
          (rs, row) -> new Person(
              rs.getString(1),
              rs.getString(2))
      ).forEach(person -> logger.info("Found <" + person + "> in the database."));
    }
    if (jobExecution.getStatus() == BatchStatus.FAILED) {
      jobExecution.getFailureExceptions().forEach(
          (jobException) -> {
            if (findRootCause(jobException) instanceof StartLimitExceededException) {
              try {
                batchJdbcTemplate.update("DELETE FROM batch_step_execution_context");
                batchJdbcTemplate.update("DELETE FROM batch_step_execution");
                batchJdbcTemplate.update("DELETE FROM batch_step_execution_seq");
              } catch (Exception ex) {
                logger.error(ex.getMessage());
              }
            }
          }
      );
    }
  }

  private Throwable findRootCause(Throwable initialException) {
    while (initialException != null) {
      if (initialException.getCause() == null) {
        break;
      }
      initialException = initialException.getCause();
    }
    return initialException;
  }
}
