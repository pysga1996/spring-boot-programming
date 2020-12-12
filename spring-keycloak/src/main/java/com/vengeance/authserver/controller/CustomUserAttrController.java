package com.vengeance.authserver.controller;

import org.springframework.stereotype.Controller;

@Controller
public class CustomUserAttrController {

//  @GetMapping(path = "/users")
//  public String getUserInfo(Model com.vengeance.authserver.model) {
//
//    KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext()
//        .getAuthentication();
//
//    final Principal principal = (Principal) authentication.getPrincipal();
//
//    String dob = "";
//
//    if (principal instanceof KeycloakPrincipal) {
//
//      KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
//      IDToken token = kPrincipal.getKeycloakSecurityContext()
//          .getIdToken();
//
//      Map<String, Object> customClaims = token.getOtherClaims();
//
//      if (customClaims.containsKey("DOB")) {
//        dob = String.valueOf(customClaims.get("DOB"));
//      }
//    }
//
//    com.vengeance.authserver.model.addAttribute("username", principal.getName());
//    com.vengeance.authserver.model.addAttribute("dob", dob);
//    return "userInfo";
//  }

}