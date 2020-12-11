package com.mongodb.listeners;

import com.mongodb.models.documents.NavigationItem;
import com.mongodb.models.documents.Role;
import com.mongodb.models.documents.User;
import com.mongodb.services.NavigationItemService;
import com.mongodb.services.RoleService;
import com.mongodb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Component
@Transactional
public class DataSeedingListener {

  private boolean alreadySetup = false;

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

  private NavigationItemService navigationItemService;

  @Autowired
  public void setNavigationItemService(NavigationItemService navigationItemService) {
    this.navigationItemService = navigationItemService;
  }

  public void onApplicationEvent() {
    if (alreadySetup) {
      return;
    }
    createRoleIfNotFound("ROLE_ADMIN");
    createRoleIfNotFound("ROLE_USER");
    createAccounts();
    alreadySetup = true;
  }

  private void createRoleIfNotFound(String authority) {
    Role role = roleService.findByAuthority(authority);
    if (role == null) {
      role = new Role(authority);
      roleService.save(role);
    }
  }

  private void createAccounts() {

    String password;
    String name = "Mongo Member";

    Collection<NavigationItem> itemListOfMember = new ArrayList<>();
    itemListOfMember.add(navigationItemService.findByTitle("Application Mongo"));
    itemListOfMember.add(navigationItemService.findByTitle("Package Mongo"));
    itemListOfMember.add(navigationItemService.findByTitle("Tools & Components Mongo"));

    // Member account
    String username = "member";
    HashSet<Role> roles2 = new HashSet<>();
    if (!userService.findByUsername(username).isPresent()) {
      password = passwordEncoder.encode("zero123456");
      roles2.add(roleService.findByAuthority("ROLE_USER"));
      User member = new User(null, username, password, roles2, true, true, true, true, name,
          "https://cdn.iconscout.com/icon/free/png-512/mongodb-3-1175138.png",
          "vutatthanh.hl96@gmail.com", itemListOfMember);
      userService.save(member);
    }

    // Admin account
    username = "admin";

    Collection<NavigationItem> itemListOfAdmin = new ArrayList<>();
    itemListOfAdmin.add(navigationItemService.findByTitle("UI Components Redis"));
    itemListOfAdmin.add(navigationItemService.findByTitle("Font Icons Redis"));
    itemListOfAdmin.add(navigationItemService.findByTitle("Theme Settings Redis"));

    if (!userService.findByUsername(username).isPresent()) {
      password = passwordEncoder.encode("zero123456");
      name = "Redis Master";
      roles2.add(roleService.findByAuthority("ROLE_ADMIN"));
      User admin = new User(null, username, password, roles2, true, true, true, true, name,
          "https://cdn4.iconfinder.com/data/icons/redis-2/1451/Untitled-2-512.png",
          "thanhvt@vissoft.vn", itemListOfAdmin);
      userService.save(admin);
    }
  }
}