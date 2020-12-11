package com.tutorial.quartz.configurations;

import com.tutorial.quartz.models.SampleJob;
import org.quartz.*;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * @author thanhvt
 * @project spring-quartz-demo
 */


@Configuration
public class QuartzConfiguration {

    // bean style
    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(SampleJob.class)
            .storeDurably()
            .withIdentity("Qrtz_Job_Detail")
            .withDescription("Invoke Sample Job service...")
            .build();
    }

    // builder style
//    @Bean
//    public JobDetailFactoryBean jobDetail() {
//        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
//        jobDetailFactory.setJobClass(SampleJob.class);
//        jobDetailFactory.setDescription("Invoke Sample Job service...");
//        jobDetailFactory.setDurability(true);
//        return jobDetailFactory;
//    }

    // bean style
    @Bean
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
            .withIdentity("Qrtz_Trigger")
            .withDescription("Sample trigger")
            .withSchedule(simpleSchedule().repeatForever().withIntervalInHours(1))
            .build();
    }

    // builder style
//    @Bean
//    public SimpleTriggerFactoryBean trigger(JobDetail job) {
//        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
//        trigger.setJobDetail(job);
//        trigger.setRepeatInterval(3600000);
//        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
//        return trigger;
//    }


    @Bean
    public Scheduler scheduler(Trigger trigger, JobDetail job, SchedulerFactoryBean factory)
        throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
        return scheduler;
    }

//    @Bean
//    public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job, DataSource quartzDataSource) {
//        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
//        schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
//
//        schedulerFactory.setJobFactory(springBeanJobFactory());
//        schedulerFactory.setJobDetails(job);
//        schedulerFactory.setTriggers(trigger);
//        schedulerFactory.setDataSource(quartzDataSource);
//        return schedulerFactory;
//    }

//    @Bean
//    public SpringBeanJobFactory springBeanJobFactory() {
//        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
//        jobFactory.setApplicationContext(applicationContext);
//        return jobFactory;
//    }

//    @Bean
//    @QuartzDataSource
//    public DataSource quartzDataSource() {
//        return DataSourceBuilder.create().build();
//    }
}
