package vn.vissoft.ems.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO implements Serializable {

  // INFO USER
  private Long userId;
  private String code;
  private String userName;
  private String email;
  private String password;
  private String sessionToken;
  private Long expireTime;
  private Integer locked;
  private String avatar;
  // INFO ROLES
  private String roles;

  public LoginDTO(String userName, String password) {
    this.setUserName(userName);
    this.setPassword(password);
  }

}
