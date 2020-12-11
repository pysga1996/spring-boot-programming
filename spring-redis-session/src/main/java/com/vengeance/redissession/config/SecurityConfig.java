package com.vengeance.redissession.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //HTTPS port
    @Value("${server.port}")
    private int httpsPort;

    //HTTP port
    @Value("${http.port}")
    private int httpPort;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser(adminUsername)
            .password(this.passwordEncoder.encode(adminPassword))
            .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .requiresChannel().antMatchers("/*").requiresSecure().and()
            .portMapper().http(httpPort).mapsTo(httpsPort).and()
            .httpBasic().and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/").hasRole("ADMIN")
            .anyRequest().authenticated();
    }
}
