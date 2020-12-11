package com.tutorial.soap.configurations;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.xml.validation.XmlValidator;
import org.springframework.xml.validation.XmlValidatorFactory;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.xml.xsd.XsdSchemaCollection;

import java.io.IOException;

@Component
public class CustomXsdSchemaCollection implements XsdSchemaCollection {

    private final XsdSchema[] xsdSchemas;

    private final Resource[] resources;

    public CustomXsdSchemaCollection(XsdSchema[] xsdSchemas, Resource[] resources) {
        this.xsdSchemas = xsdSchemas;
        this.resources = resources;
    }

    @Override
    public XsdSchema[] getXsdSchemas() {
        return xsdSchemas;
    }

    @Override
    public XmlValidator createValidator() {
        try {
            return XmlValidatorFactory.createValidator(resources,
                    "http://www.w3.org/2001/XMLSchema");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
