package com.mongodb.repositories;

import com.mongodb.models.documents.PasswordResetToken;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {

  PasswordResetToken findByToken(String token);

  List<PasswordResetToken> findAllByExpiryDateBefore(Date date);

  @DeleteQuery
  void deleteByToken(String token);
}
