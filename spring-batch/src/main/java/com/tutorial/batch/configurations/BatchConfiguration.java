package com.tutorial.batch.configurations;

import com.tutorial.batch.listeners.JobCompletionNotificationListener;
import com.tutorial.batch.models.Person;
import com.tutorial.batch.processors.PersonItemProcessor;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

/**
 * @author thanhvt
 * @project spring-batch-demo
 */

@Configuration
@Primary
@DependsOn("batchConfigInitializer")
public class BatchConfiguration {

    public JobBuilderFactory jobBuilderFactory;

    public StepBuilderFactory stepBuilderFactory;

    private StepExecutionListener stepCountListener;

    private ExceptionHandler stepExceptionHandler;

    private SkipPolicy startLimitSkipper;

    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory,
        StepExecutionListener stepCountListener, ExceptionHandler stepExceptionHandler,
        SkipPolicy startLimitSkipper) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.stepCountListener = stepCountListener;
        this.stepExceptionHandler = stepExceptionHandler;
        this.startLimitSkipper = startLimitSkipper;
    }

    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
            .name("personItemReader")
            .resource(new ClassPathResource("sample-data.csv"))
            .delimited()
            .delimiter(",")
            .names("firstName", "lastName")
            .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }})
            .build();
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    @DependsOn("mainDS")
    public JdbcBatchItemWriter<Person> writer(@Qualifier("mainDS") DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
//                .preventRestart()
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory.get("step1")
            .<Person, Person>chunk(10)
            .reader(reader())
            .processor(processor())
            .writer(writer)
            .faultTolerant().skipPolicy(startLimitSkipper)
            .allowStartIfComplete(true)
            .listener(stepCountListener)
            .exceptionHandler(stepExceptionHandler)
            .startLimit(3)
//                .exceptionHandler(stepExceptionHandler)
            .build();
    }
}
