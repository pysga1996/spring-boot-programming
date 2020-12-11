package com.tutorial.batch.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thanhvt
 * @project spring-batch-demo
 */

@RestController
@RequestMapping("/person-job")
public class PersonJobController {

  private static final Logger logger = LoggerFactory.getLogger(PersonJobController.class);

  private final Job importUserJob;

  private final Step step1;

  private final JobLauncher jobLauncher;

  @Autowired
  public PersonJobController(Job importUserJob, JobLauncher jobLauncher, Step step1) {
    this.importUserJob = importUserJob;
    this.jobLauncher = jobLauncher;
    this.step1 = step1;
  }

  @GetMapping(params = {"action=run"})
  public ResponseEntity<Void> runPersonJob() {
    try {
      jobLauncher.run(importUserJob, new JobParameters());
      logger.info("Job finished successfully!");
      logger.info("Step start limit = " + step1.getStartLimit());
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (JobExecutionException e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
