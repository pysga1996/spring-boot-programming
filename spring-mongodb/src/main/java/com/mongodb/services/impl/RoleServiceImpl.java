package com.mongodb.services.impl;

import com.mongodb.models.documents.Role;
import com.mongodb.repositories.RoleRepository;
import com.mongodb.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  private RoleRepository roleRepository;

  @Autowired
  public void setRoleRepository(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role findByAuthority(String authority) {
    return roleRepository.findByAuthority(authority);
  }

  @Override
  public void save(Role role) {
    roleRepository.save(role);
  }
}
