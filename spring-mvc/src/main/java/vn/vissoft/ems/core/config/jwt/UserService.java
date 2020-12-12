package vn.vissoft.ems.core.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.vissoft.ems.core.model.User;
import vn.vissoft.ems.core.services.UsersService;

import javax.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UsersService userService;

  @Override
  public UserDetails loadUserByUsername(String username) {
    // Kiểm tra xem user có tồn tại trong database không?
    User user = userService.findUserByUserName(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return new CustomUserDetails(user);
  }

  // doFilter sẽ sử dụng hàm này
  @Transactional
  public UserDetails loadUserById(Long id) {
    User user = userService.findUserByUserId(id);
    if (user != null) {
      return new CustomUserDetails(user);
    } else {
      return null;
    }
  }

}
