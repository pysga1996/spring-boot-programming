package com.stomp.controllers;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class CommunityController {

    private final SimpUserRegistry simpUserRegistry;

    @Autowired
    public CommunityController(
        SimpUserRegistry simpUserRegistry) {
        this.simpUserRegistry = simpUserRegistry;
    }

    @GetMapping("/users/online")
    public ResponseEntity<Map<String, Boolean>> getOnlineUser() {
        return ResponseEntity.ok(simpUserRegistry.getUsers().stream().collect(
            Collectors.toMap(SimpUser::getName, value -> true)));
    }

}
