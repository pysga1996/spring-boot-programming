package com.tutorial.batch.configurations;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author thanhvt
 * @project spring-batch-demo
 */

@Configuration("batchConfigInitializer")
@EnableBatchProcessing
public class BatchConfigInitializer {

    @Bean
    @Primary
    public BatchConfigurer configurer(@Qualifier("batchJobDS") DataSource dataSource) {
        return new DefaultBatchConfigurer(dataSource);
    }
}
