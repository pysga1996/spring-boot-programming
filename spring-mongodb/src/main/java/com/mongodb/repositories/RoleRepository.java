package com.mongodb.repositories;

import com.mongodb.models.documents.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

  Role findByAuthority(String authority);
}
