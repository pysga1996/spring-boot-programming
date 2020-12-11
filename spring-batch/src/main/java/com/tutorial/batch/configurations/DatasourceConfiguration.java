package com.tutorial.batch.configurations;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author thanhvt
 * @project spring-batch-demo
 */

@Configuration
public class DatasourceConfiguration {

  @Bean("mainDSProps")
  @Primary
  @ConfigurationProperties("spring.datasource")
  public DataSourceProperties mainDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean("pysgaDSProps")
  @ConfigurationProperties("pysga.datasource")
  public DataSourceProperties batchDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean("mainDS")
  @Primary
  public HikariDataSource mainDatasource(
      @Qualifier("mainDSProps") DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }

  @Bean("batchJobDS")
  public HikariDataSource batchDataSource(
      @Qualifier("pysgaDSProps") DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }

  @Bean("mainJdbcTemplate")
  @Primary
  public JdbcTemplate mainJdbcTemplate(@Qualifier("mainDS") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean("batchJdbcTemplate")
  public JdbcTemplate batchJdbcTemplate(@Qualifier("batchJobDS") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

}
