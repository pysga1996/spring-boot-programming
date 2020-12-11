package com.mongodb.configurations;

import com.mongodb.customizations.CustomTokenEnhancer;
import com.mongodb.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private UserDetailsService userDetailsService;

  @Autowired
  public void setUserDetailsService(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean(name = "customUserDetailService")
  public UserDetailsService userDetailsService() {
    return new UserServiceImpl();
  }

  @Bean(name = "authenticationManagerBean")
  @Override
  public AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }


  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    return new CustomTokenEnhancer();
  }

  @Autowired
  public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder() )
//                .withUser("john").password(passwordEncoder().encode("123")).roles("USER").and()
//                .withUser("tom").password(passwordEncoder().encode("111")).roles("ADMIN").and()
//                .withUser("user1").password(passwordEncoder().encode("pass")).roles("USER").and()
//                .withUser("admin").password(passwordEncoder().encode("nimda")).roles("ADMIN");
    auth.authenticationProvider(new CustomAuthenticationProvider());
    auth.userDetailsService(userDetailsService);
  }
}
