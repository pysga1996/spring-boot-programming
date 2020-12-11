package com.mongodb.services;

import com.mongodb.models.documents.Role;

public interface RoleService {

  Role findByAuthority(String authority);

  void save(Role role);
}
