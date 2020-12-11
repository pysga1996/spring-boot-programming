package com.tutorial.soap.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;

import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Value("${auth.username}")
    private String authUsername;

    @Value("${auth.password}")
    private String authPassword;

    @Bean
    SimplePasswordValidationCallbackHandler callbackHandler() {
        SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
        callbackHandler.setUsersMap(Collections.singletonMap(authUsername, authPassword));
        return callbackHandler;
    }

}
