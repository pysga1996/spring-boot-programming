package com.mongodb.customizations;


import com.mongodb.daos.NavigationItemDao;
import com.mongodb.models.documents.Role;
import com.mongodb.models.documents.User;
import com.mongodb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomTokenEnhancer extends JwtAccessTokenConverter implements TokenEnhancer {

  private UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {
    User principal = (User) authentication.getPrincipal();
    final Map<String, Object> additionalInfo = new TreeMap<>();
    final Map<String, Object> userInfo = new TreeMap<>();
    final Map<String, Object> userData = new TreeMap<>();
    final Set<Map<String, Object>> roles = new HashSet<>();
    Optional<User> user = userRepository.findByUsername(principal.getUsername());
    if (user.isPresent()) {
      userData.put("userName", user.get().getUsername());
      userData.put("password", null);
      userData.put("accountNonExpired", user.get().isAccountNonExpired());
      userData.put("accountNonLocked", user.get().isAccountNonLocked());
      userData.put("credentialsNonExpired", user.get().isCredentialsNonExpired());
      userData.put("enable", user.get().isEnabled());
      userData.put("userId", user.get().getId());
      userData.put("name", user.get().getName());
      userData.put("avatar", user.get().getAvatar());
      userData.put("email", user.get().getEmail());
      for (Role role : user.get().getAuthorities()) {
        Map<String, Object> roleMap = new TreeMap<>();
        roleMap.put("roleId", role.getId());
        roleMap.put("roleName", role.getAuthority());
        roles.add(roleMap);
      }
      userInfo.put("userData", userData);
      userInfo.put("roles", roles);
      userInfo.put("items", user.get().getNavigationItemCollection());
      additionalInfo.put("userInfo", userInfo);
    }
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

    return super.enhance(accessToken, authentication);
  }

}
