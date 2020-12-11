package com.tutorial.soap.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
public class XsdConfig {

    @Bean
    public XsdSchema countriesSchema(Resource countriesResource) {
        return new SimpleXsdSchema(countriesResource);
    }

    @Bean
    public XsdSchema classSchema(Resource classResource) {
        return new SimpleXsdSchema(classResource);
    }

}
