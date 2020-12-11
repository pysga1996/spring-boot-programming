package com.tutorial.swagger.configurations;

import static springfox.documentation.spring.web.paths.Paths.removeAdjacentForwardSlashes;

import com.tutorial.swagger.plugins.EmailAnnotationPlugin;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.paths.DefaultPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Import({SpringDataRestConfiguration.class, BeanValidatorPluginsConfiguration.class})
@EnableSwagger2
//@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .pathProvider(new MyPathProvider())
            .select()
            .apis(RequestHandlerSelectors.any())
//                .apis(RequestHandlerSelectors.basePackage("com.tutorial.swagger.controllers"))
            .paths(PathSelectors.any())
            .build().apiInfo(apiInfo())
            .useDefaultResponseMessages(false)
            .globalResponseMessage(RequestMethod.GET,
                new ArrayList<>(Arrays.asList(new ResponseMessageBuilder()
                        .code(500)
                        .message("500 message")
                        .responseModel(new ModelRef("string"))
                        .build(),
                    new ResponseMessageBuilder()
                        .code(403)
                        .message("Forbidden!")
                        .build())))
            .securityContexts(Collections.singletonList(securityContext()))
            .securitySchemes(Collections.singletonList(securityScheme()))

            ;
    }

    @Bean
    public EmailAnnotationPlugin emailPlugin() {
        return new EmailAnnotationPlugin();
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
            .clientId("foo")
            .clientSecret("secret")
            .scopeSeparator(" ")
//                .useBasicAuthenticationWithAccessCodeGrant(true)
            .build();
    }

    private SecurityScheme securityScheme() {
//        GrantType grantType = new AuthorizationCodeGrantBuilder()
//                .tokenEndpoint(new TokenEndpoint("http://localhost:8080/spring-swagger" + "oauth/token", "oauthtoken"))
//                .tokenRequestEndpoint(
//                        new TokenRequestEndpoint("http://localhost:8080/spring-swagger" + "/authorize", "foo", "secret"))
//                .build();

//        return new OAuthBuilder().name("spring_oauth")
//                .grantTypes(Collections.singletonList(grantType))
//                .scopes(Arrays.asList(scopes()))
//                .build();
        return new BasicAuth("spring_oauth");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(
                Collections.singletonList(new SecurityReference("spring_oauth", scopes())))
            .forPaths(PathSelectors.ant("/**"))
            .build();
    }

    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {
            new AuthorizationScope("read", "for read operations"),
            new AuthorizationScope("write", "for write operations"),
            new AuthorizationScope("foo", "Access foo API")};
        return scopes;
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
            "Ứng dụng web sử dụng Swagger",
            "Kiểm thử Restful API bằng Swagger.",
            "1.0",
            "Điều khoản: ...",
            new Contact("Vũ Tất Thành", "https://www.facebook.com/profile.php?id=100002900027273",
                "pysga1996@gmail.com"),
            "", "", Collections.emptyList());
    }

    private class MyPathProvider extends DefaultPathProvider {

        @Override
        public String getOperationPath(String operationPath) {
            if (operationPath.startsWith(contextPath)) {
                operationPath = operationPath.substring(contextPath.length());
            }
            return removeAdjacentForwardSlashes(
                UriComponentsBuilder.newInstance().replacePath(operationPath)
                    .build().toString());
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
