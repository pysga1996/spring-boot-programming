package com.mongodb.repositories;

import com.mongodb.models.documents.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

  Optional<User> findByUsername(String username);

  User findByEmail(String email);
}
