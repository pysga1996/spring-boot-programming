package com.tutorial.swagger.controllers;

import com.tutorial.swagger.models.User;
import com.tutorial.swagger.repositories.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    private final UserRepository userRepository;

    @Autowired
    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/user-list")
    public ResponseEntity<List<User>> custom() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
}
