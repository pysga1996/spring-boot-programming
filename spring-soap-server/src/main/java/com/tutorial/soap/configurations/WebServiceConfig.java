package com.tutorial.soap.configurations;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import javax.servlet.http.HttpServlet;
import java.util.List;

/**
 * @author thanhvt
 * @project spring-soap-web-service-demo
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    private final PayloadLoggingInterceptor payloadLoggingInterceptor;

    private final PayloadValidatingInterceptor payloadValidatingInterceptor;

    private final XwsSecurityInterceptor xwsSecurityInterceptor;

    public WebServiceConfig(PayloadLoggingInterceptor payloadLoggingInterceptor, PayloadValidatingInterceptor payloadValidatingInterceptor, XwsSecurityInterceptor xwsSecurityInterceptor) {
        this.payloadLoggingInterceptor = payloadLoggingInterceptor;
        this.payloadValidatingInterceptor = payloadValidatingInterceptor;
        this.xwsSecurityInterceptor = xwsSecurityInterceptor;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(payloadLoggingInterceptor);
        interceptors.add(payloadValidatingInterceptor);
        interceptors.add(xwsSecurityInterceptor);
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }
}

