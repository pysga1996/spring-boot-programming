package com.mongodb.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Base64;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  private PasswordEncoder passwordEncoder;

  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  private JwtAccessTokenConverter jwtAccessTokenConverter;

  @Autowired
  public void setJwtAccessTokenConverter(JwtAccessTokenConverter jwtAccessTokenConverter) {
    this.jwtAccessTokenConverter = jwtAccessTokenConverter;
  }

  private AuthenticationManager authenticationManager;

  @Value("${rsa.private.key}")
  private String rsaPrivateKey;

  @Value("${rsa.public.key}")
  private String rsaPublicKey;

  @Autowired
  @Qualifier("authenticationManagerBean")
  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    try {
      oauthServer.tokenKeyAccess("permitAll()")
          .checkTokenAccess("isAuthenticated()");
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.applyPermitDefaultValues();
      config.addAllowedHeader("*");
      config.addAllowedMethod("*");
      config.addAllowedOrigin("*");
      source.registerCorsConfiguration("/**", config);
      CorsFilter filter = new CorsFilter(source);
      oauthServer.addTokenEndpointAuthenticationFilter(filter);
    } catch (Exception ex) {
      throw new Exception(ex);
    }
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients)
      throws Exception {
    clients.inMemory().withClient("mongo")
        .secret(passwordEncoder.encode("redis"))
        .authorizedGrantTypes("password", "refresh_token")
        .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
        .scopes("read", "write", "trust")
        .accessTokenValiditySeconds(84600)
        .refreshTokenValiditySeconds(84600 * 30);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    try {
      endpoints.tokenStore(tokenStore())
          .authenticationManager(authenticationManager)
          .accessTokenConverter(accessTokenConverter());
    } catch (Exception ex) {
      throw new Exception(ex);
    }

  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    defaultTokenServices.setSupportRefreshToken(true);
    return defaultTokenServices;
  }

  @Bean
  @Primary
  public JwtAccessTokenConverter accessTokenConverter() {
//        try {
//            KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
//            keyGenerator.initialize(1024);
//            KeyPair kp = keyGenerator.genKeyPair();
//            converter.setKeyPair(kp);
//        } catch (NoSuchAlgorithmException ex) {
//            System.out.println("Cannot generate keypair.");
//        }
    System.out.println(rsaPrivateKey);
    System.out.println(rsaPublicKey);
    jwtAccessTokenConverter.setSigningKey(
        new String(Base64.getDecoder().decode(Objects.requireNonNull(rsaPrivateKey)), UTF_8));
    jwtAccessTokenConverter.setVerifier(new RsaVerifier(
        new String(Base64.getDecoder().decode(Objects.requireNonNull(rsaPublicKey)), UTF_8)));
    return jwtAccessTokenConverter;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }
}
