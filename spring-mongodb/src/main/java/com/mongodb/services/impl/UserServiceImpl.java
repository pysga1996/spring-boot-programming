package com.mongodb.services.impl;

import com.mongodb.models.documents.PasswordResetToken;
import com.mongodb.models.documents.User;
import com.mongodb.models.documents.VerificationToken;
import com.mongodb.repositories.PasswordResetTokenRepository;
import com.mongodb.repositories.UserRepository;
import com.mongodb.repositories.VerificationTokenRepository;
import com.mongodb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Primary
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class UserServiceImpl implements UserDetailsService, UserService {

  private UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private VerificationTokenRepository verificationTokenRepository;

  @Autowired
  public void setVerificationTokenRepository(
      VerificationTokenRepository verificationTokenRepository) {
    this.verificationTokenRepository = verificationTokenRepository;
  }

  private PasswordEncoder passwordEncoder;

  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  private PasswordResetTokenRepository passwordResetTokenRepository;

  @Autowired
  public void setPasswordResetTokenRepository(
      PasswordResetTokenRepository passwordResetTokenRepository) {
    this.passwordResetTokenRepository = passwordResetTokenRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new UsernameNotFoundException(username);
    }
  }

  @Override
  public User getCurrentUser() {
    User user;
    String username;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      username = ((UserDetails) principal).getUsername();
    } else {
      username = principal.toString();
    }
    if (userRepository.findByUsername(username).isPresent()) {
      user = userRepository.findByUsername(username).get();
    } else {
      user = new User();
      user.setUsername("Anonymous");
    }
    return user;
  }

  @Override
  public List<User> userList() {
    return userRepository.findAll();
  }

  @Override
  public Optional<User> findById(String id) {
    return userRepository.findById(id);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public void save(User user) {
    userRepository.save(user);
  }

  @Override
  public void createVerificationToken(User user, String token) {
    VerificationToken myToken = new VerificationToken(token, user);
    verificationTokenRepository.save(myToken);
  }

  @Override
  public void removeVerificationToken(VerificationToken verificationToken) {
    verificationTokenRepository.delete(verificationToken);
  }

  @Override
  public void getVerificationToken(String token) throws Exception {
    if (token == null) {
      throw new InvalidTokenException("1");
    }
    VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
    if (verificationToken == null) {
      throw new InvalidTokenException("1");
    }
    User user = verificationToken.getUser();
    Calendar cal = Calendar.getInstance();
    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
      throw new InvalidTokenException("2");
    }
    user.setEnabled(true);
    userRepository.save(user);
    this.removeVerificationToken(verificationToken);
  }

  @Override
  public void createPasswordResetToken(User user, String token) {
    PasswordResetToken myToken = new PasswordResetToken(token, user);
    passwordResetTokenRepository.save(myToken);
  }

  @Override
  public void validatePasswordResetToken(String id, String token) throws Exception {
    PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);
    if ((passToken == null) || (!passToken.getUser().getId().equals(id))) {
      throw new InvalidTokenException("1");
    }
    Calendar cal = Calendar.getInstance();
    if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
      throw new InvalidTokenException("2");
    }
    User user = passToken.getUser();
    Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
        user.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  @Override
  public void removePasswordResetToken(String token) {
    passwordResetTokenRepository.deleteByToken(token);
  }

  @Override
  public void changeUserPassword(User user, String password) {
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
  }

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
