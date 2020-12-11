package com.mongodb.controllers;

import com.mongodb.events.OnRegistrationCompleteEvent;
import com.mongodb.events.OnResetPasswordEvent;
import com.mongodb.exceptions.ErrorCode;
import com.mongodb.models.documents.Role;
import com.mongodb.models.documents.User;
import com.mongodb.models.forms.GetResetPasswordTokenForm;
import com.mongodb.models.util.ApiError;
import com.mongodb.models.util.PasswordDto;
import com.mongodb.services.RoleService;
import com.mongodb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.net.URI;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/")
public class UserRestController {

  private static final String DEFAULT_ROLE = "ROLE_USER";

  private UserService userService;

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  private RoleService roleService;

  @Autowired
  public void setRoleService(RoleService roleService) {
    this.roleService = roleService;
  }

  private PasswordEncoder passwordEncoder;

  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  private ApplicationEventPublisher eventPublisher;

  @Autowired
  public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  private Environment environment;

  @Autowired
  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }

  @PreAuthorize("isAnonymous()")
  @PostMapping("/register")
  public ResponseEntity<String> createUser(@Valid @RequestBody User user, WebRequest request) {
    try {
      if (user == null) {
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
      }
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      Role role = roleService.findByAuthority(DEFAULT_ROLE);
      Set<Role> roles = new HashSet<>();
      roles.add(role);
      user.setAuthorities(roles);
      userService.save(user);
      String appUrl = request.getContextPath();
      eventPublisher
          .publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/registration-confirm")
  public ResponseEntity<String> confirmRegistration(@RequestParam("token") String token) {
    HttpHeaders headers = new HttpHeaders();
    StringBuilder uri = new StringBuilder(
        Objects.requireNonNull(environment.getProperty("FRONTEND_HOST")))
        .append("/complete-registration");
    try {
      userService.getVerificationToken(token);
      uri.append("?status=0");
      headers.setLocation(URI.create(uri.toString()));
      return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    } catch (InvalidTokenException ex) {
      uri.append("?status=").append(ex.getMessage());
      headers.setLocation(URI.create(uri.toString()));
      return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    } catch (Exception ex) {
      uri.append("?status=3");
      headers.setLocation(URI.create(uri.toString()));
      return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
  }

  @PreAuthorize("isAuthenticated()")
  @PatchMapping("/change-password")
  public ResponseEntity<ApiError> changePassword(@Valid @RequestBody PasswordDto passwordDto) {
    try {
      User user = userService.getCurrentUser();
      if (user == null) {
        throw new RuntimeException(ErrorCode.USER_NOT_FOUND.label);
      }
      if (passwordDto.getOldPassword() == null || !passwordEncoder
          .matches(passwordDto.getOldPassword(), user.getPassword())) {
        throw new RuntimeException(ErrorCode.CURRENT_PASSWORD_FALSE.label);
      }
      if (passwordDto.getNewPassword() != null && passwordDto.getRepeatedNewPassword() != null
          && passwordDto.getNewPassword().equals(passwordDto.getRepeatedNewPassword())) {
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
      } else {
        throw new RuntimeException(ErrorCode.NEW_PASSWORD_SAME_AS_OLD_PASSWORD.label);
      }

    } catch (Exception ex) {
      return new ResponseEntity<>(
          new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), new ArrayList<>()),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/check-email")
  public ResponseEntity<Boolean> checkEmail(@Valid @RequestParam("email") @Email String email) {
    try {
      User user = userService.findByEmail(email);
      return new ResponseEntity<>(user != null, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("permitAll()")
  @PostMapping("/reset-password")
  public ResponseEntity<Void> resetPassword(
      @Valid @RequestBody GetResetPasswordTokenForm getResetPasswordTokenForm, WebRequest request) {
    try {
      String userEmail = getResetPasswordTokenForm.getEmail();
      User user = userService.findByEmail(userEmail);
      if (user == null) {
        throw new RuntimeException(ErrorCode.USER_NOT_FOUND.label);
      }
      String appUrl = request.getContextPath();
      eventPublisher.publishEvent(new OnResetPasswordEvent(user, request.getLocale(), appUrl));
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      if (ex.getMessage().equals("1")) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } else {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
  }

  @PreAuthorize("permitAll()")
  @GetMapping(value = "/reset-password", params = {"id", "token"})
  public ResponseEntity showChangePasswordPage(@RequestParam("id") String id,
      @RequestParam("token") String token) {
    HttpHeaders headers = new HttpHeaders();
    StringBuilder uri = new StringBuilder(
        Objects.requireNonNull(environment.getProperty("FRONTEND_HOST"))).append("/reset-password");
    try {
      userService.validatePasswordResetToken(id, token);
      uri.append("?status=0").append("&id=").append(id).append("&token=").append(token);
      headers.setLocation(URI.create(uri.toString()));
      return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    } catch (InvalidTokenException ex) {
      uri.append("?status=").append(ex.getMessage());
      headers.setLocation(URI.create(uri.toString()));
      return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    } catch (Exception ex) {
      uri.append("?status=3");
      headers.setLocation(URI.create(uri.toString()));
      return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
  }

  @PreAuthorize("permitAll()")
  @PutMapping(value = "/reset-password", params = {"id", "token"})
  public ResponseEntity<String> savePassword(@Valid @RequestBody PasswordDto passwordDto,
      @RequestParam("id") String id, @RequestParam("token") String token) {
    try {
      userService.validatePasswordResetToken(id, token);
      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      userService.changeUserPassword(user, passwordDto.getNewPassword());
      userService.removePasswordResetToken(token);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (InvalidTokenException ex) {
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/user")
  public ResponseEntity<List<User>> userList() {
    List<User> users = userService.userList();
    return new ResponseEntity<>(users, HttpStatus.OK);
  }
}
