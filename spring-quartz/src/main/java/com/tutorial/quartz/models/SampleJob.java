package com.tutorial.quartz.models;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author thanhvt
 * @project spring-quartz-demo
 */
@Component
public class SampleJob implements Job {

  private final SampleJobService jobService;

  @Autowired
  public SampleJob(SampleJobService jobService) {
    this.jobService = jobService;
  }

  public void execute(JobExecutionContext context) {
    jobService.executeSampleJob();
  }
}
