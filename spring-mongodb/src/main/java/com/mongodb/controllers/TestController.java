package com.mongodb.controllers;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class TestController {

  @PostMapping("/test")
  public ResponseEntity<Response> submitForm(@RequestBody DemoObject demoObject) {
    Response response = new Response();
    Status status = new Status();
    response.setStatus(status);
    try {
      status.setCode("200");
      status.setMessage("Success");
      response.setData("Balance is " + demoObject.getBalance());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    } catch (Exception ex) {
      status.setCode("9999");
      status.setMessage("Failed");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  @Data
  static class Status {

    private String code;
    private String message;
  }

  @Data
  static class Response {

    private Status status;
    private Object data;
  }

  @Data
  static class DemoObject {

    private int balance;
  }
}
