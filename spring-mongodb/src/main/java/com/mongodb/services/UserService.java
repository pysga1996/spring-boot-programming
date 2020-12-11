package com.mongodb.services;

import com.mongodb.models.documents.User;
import com.mongodb.models.documents.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface UserService {

  User getCurrentUser();

  List<User> userList();

  Optional<User> findById(String id);

  Optional<User> findByUsername(String username);

  void save(User user);

  void createVerificationToken(User user, String token);

  void removeVerificationToken(VerificationToken verificationToken);

  void getVerificationToken(String token) throws Exception;

  void createPasswordResetToken(User user, String token);

  void validatePasswordResetToken(String id, String token) throws Exception;

  void removePasswordResetToken(String tokenId);

  void changeUserPassword(User user, String newPassword);

  User findByEmail(String userEmail);
}
