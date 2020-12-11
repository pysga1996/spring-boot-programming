package com.vengeance.redissession.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http2.Http2Protocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    //HTTPS port
    @Value("${server.port}")
    private int httpsPort;

    //HTTP port
    @Value("${http.port}")
    private int httpPort;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Let's configure additional connector to enable support for both HTTP and HTTPS
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            // config redirect rule in spring security instead
      /*
      @Override
      protected void postProcessContext(Context context) {
        SecurityConstraint securityConstraint = new SecurityConstraint();
        // set to CONFIDENTIAL to automatically redirect from http to https port
        // securityConstraint.setUserConstraint("CONFIDENTIAL");
        securityConstraint.setUserConstraint("NONE");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        securityConstraint.addCollection(collection);
        context.addConstraint(securityConstraint);
      }
       */
        };
        // set http2 enabled in properties instead
        // tomcat.addConnectorCustomizers((connector -> connector.addUpgradeProtocol(new Http2Protocol())));
        tomcat.addAdditionalTomcatConnectors(getHttpConnector());
        return tomcat;
    }

    private Connector getHttpConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setPort(httpPort);
    /* config in spring security instead
     connector.setScheme("http");
     connector.setSecure(false);
     connector.setRedirectPort(httpsPort);
     */
        connector.addUpgradeProtocol(new Http2Protocol());
        return connector;
    }

}
