package com.mongodb.springmongodb;

import com.mongodb.listeners.DataSeedingListener;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http2.Http2Protocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = "com.mongodb")
@EnableMongoRepositories(basePackages = {"com.mongodb.daos", "com.mongodb.repositories"})
public class SpringMongodbApplication {

    private DataSeedingListener dataSeedingListener;

    @Value("${server.connector.port}")
    private short httpPort;

    @Value("${server.port}")
    private short httpsPort;

    @Autowired
    public void setDataSeedingListener(DataSeedingListener dataSeedingListener) {
        this.dataSeedingListener = dataSeedingListener;
    }

    @PostConstruct
    public void setProperty() {

    }

    public static void main(String[] args) {
        SpringApplication.run(SpringMongodbApplication.class, args);
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                // set to CONFIDENTIAL to automatically redirect from http to https port
                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                securityConstraint.setUserConstraint("NONE");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        // set http2 enabled in properties instead
//        tomcat.addConnectorCustomizers((connector -> {
//            connector.addUpgradeProtocol(new Http2Protocol());
//        }));
        tomcat.addAdditionalTomcatConnectors(getHttpConnector());
        return tomcat;
    }

    private Connector getHttpConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(httpPort);
        connector.setSecure(false);
        connector.setRedirectPort(httpsPort);
        connector.addUpgradeProtocol(new Http2Protocol());
        return connector;
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MongoMappingContext context) {
        MappingMongoConverter converter = new MappingMongoConverter(
                new DefaultDbRefResolver(mongoDbFactory), context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return new MongoTemplate(mongoDbFactory, converter);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @EventListener(ContextRefreshedEvent.class)
    public void dataSeeding() {
        dataSeedingListener.onApplicationEvent();
    }


}
