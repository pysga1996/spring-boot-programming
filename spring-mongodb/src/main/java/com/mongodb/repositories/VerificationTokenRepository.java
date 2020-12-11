package com.mongodb.repositories;

import com.mongodb.models.documents.User;
import com.mongodb.models.documents.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String> {

  VerificationToken findByToken(String token);

  VerificationToken findByUser(User user);

  List<VerificationToken> findAllByExpiryDateBefore(Date date);
}
