package com.mongodb.configurations;

import com.mongodb.services.impl.UserServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

  public CustomAuthenticationProvider() {
    super();
    this.setUserDetailsService(new UserServiceImpl());
  }
}
