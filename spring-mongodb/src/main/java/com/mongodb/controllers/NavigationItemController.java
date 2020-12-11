package com.mongodb.controllers;

import com.mongodb.models.documents.NavigationItem;
import com.mongodb.models.documents.User;
import com.mongodb.services.NavigationItemService;
import com.mongodb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping(("/navigation-item"))
public class NavigationItemController {

  private NavigationItemService navigationItemService;

  @Autowired
  public void setNavigationItemService(NavigationItemService navigationItemService) {
    this.navigationItemService = navigationItemService;
  }

  private UserService userService;

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/list")
  public ResponseEntity<Collection<NavigationItem>> navigationItemList() {
    try {
      if (userService.getCurrentUser().getUsername().equals("Anonymous")) {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(userService.getCurrentUser().getNavigationItemCollection(),
            HttpStatus.OK);
      }
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(value = "/list", params = {"user-id"})
  public ResponseEntity<Collection<NavigationItem>> navigationItemList(
      @RequestParam("user-id") String userId) {
    try {
      Optional<User> user = userService.findById(userId);
      return user
          .map(value -> new ResponseEntity<>(value.getNavigationItemCollection(), HttpStatus.OK))
          .orElseGet(() -> new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK));
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(value = "/list", params = {"username"})
  public ResponseEntity<Collection<NavigationItem>> navigationItemListByUsername(
      @RequestParam("username") String username) {
    try {
      Optional<User> user = userService.findByUsername(username);
      return user
          .map(value -> new ResponseEntity<>(value.getNavigationItemCollection(), HttpStatus.OK))
          .orElseGet(() -> new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK));
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/detail")
  public ResponseEntity<NavigationItem> navigationItemDetail(@RequestParam("id") String id) {
    try {
      return new ResponseEntity<>(navigationItemService.findById(id), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
