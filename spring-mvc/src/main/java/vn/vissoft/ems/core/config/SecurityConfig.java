package vn.vissoft.ems.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import vn.vissoft.ems.core.config.jwt.UserService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private Environment env;
  @Autowired
  private UserService userService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.headers().frameOptions().disable();
    http.csrf().ignoringAntMatchers("/**").disable();
    http.cors().configurationSource(corsConfigurationSource());
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    String corsMapping = env.getProperty("security.web.cors-mapping");
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);
    if (StringUtils.isEmpty(corsMapping)) {
      configuration.addAllowedOrigin("*");
    } else {
      String[] corsArray = corsMapping.split(",");
      for (String cors : corsArray) {
        configuration.addAllowedOrigin(cors);
      }
    }
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // START - BLOCK JWT
  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
    auth.userDetailsService(userService) // Cung cáp userservice cho spring security
        .passwordEncoder(passwordEncoder()); // cung cấp password encoder
  }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    // Get AuthenticationManager bean
    return super.authenticationManagerBean();
  }
  // END - BLOCK JWT
}

