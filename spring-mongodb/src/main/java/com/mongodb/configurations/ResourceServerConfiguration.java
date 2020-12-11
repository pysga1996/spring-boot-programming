package com.mongodb.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    http.requiresChannel().anyRequest().requiresSecure().and()
        .authorizeRequests()
        .antMatchers("/api/admin/**").access("hasRole('ADMIN')")
        .antMatchers("/oauth/token", "/oauth/check_token", "/api/login", "/api/register",
            "/api/user", "/api/reset-password", "/api/check-email").permitAll()
        .antMatchers("/api/test").permitAll()
        .antMatchers("/api/**").authenticated()
        .and().cors()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

}
