package com.tutorial.soap.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;

@Component
public class CustomSecurityInterceptor extends XwsSecurityInterceptor {

    @Autowired
    public CustomSecurityInterceptor(SimplePasswordValidationCallbackHandler simplePasswordValidationCallbackHandler) {
        this.setCallbackHandler(simplePasswordValidationCallbackHandler);
        this.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
    }
}
