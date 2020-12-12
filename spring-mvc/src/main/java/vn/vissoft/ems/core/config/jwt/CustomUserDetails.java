package vn.vissoft.ems.core.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.vissoft.ems.core.model.User;

import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

  User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton((GrantedAuthority) () ->
        // user.getGroupRole() != null ? user.getGroupRole().getCode() : ""
        "NON_GROUP");
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUserName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  // Check isLocked
  @Override
  public boolean isAccountNonLocked() {
    if (user.isLocked()) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
