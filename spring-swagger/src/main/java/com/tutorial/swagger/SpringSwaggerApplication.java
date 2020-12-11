package com.tutorial.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.tutorial.swagger.repositories")
public class SpringSwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSwaggerApplication.class, args);
    }

}
