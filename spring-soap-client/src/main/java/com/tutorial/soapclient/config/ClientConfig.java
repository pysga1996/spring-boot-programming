package com.tutorial.soapclient.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

@Configuration
public class ClientConfig {

    private static final Logger logger = LogManager.getLogger(ClientConfig.class);

    @Value("${auth.username}")
    private String authUsername;

    @Value("${auth.password}")
    private String authPassword;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.tutorial.soapclient.models");
        return marshaller;
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() {
        Wss4jSecurityInterceptor security = new Wss4jSecurityInterceptor();

        // Adds "Timestamp" and "UsernameToken" sections in SOAP header
        security.setSecurementActions(WSHandlerConstants.USERNAME_TOKEN);

        // Set values for "UsernameToken" sections in SOAP header
        security.setSecurementPasswordType(WSConstants.PW_TEXT);
        security.setSecurementUsername(authUsername);
        security.setSecurementPassword(authPassword);

        return security;

    }
}
