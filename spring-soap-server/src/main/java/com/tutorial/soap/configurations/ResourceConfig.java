package com.tutorial.soap.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class ResourceConfig {

    @Bean
    public Resource countriesResource() {
        return new ClassPathResource("countries.xsd");
    }

    @Bean
    public Resource classResource() {
        return new ClassPathResource("class.xsd");
    }
}
