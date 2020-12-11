package com.tutorial.cache.controllers;

import com.tutorial.cache.models.Customer;
import com.tutorial.cache.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thanhvt
 * @project spring-cache-demo
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/address")
    public ResponseEntity<String> getAddress(@RequestBody Customer customer) {
        try {
            return new ResponseEntity<>(customerService.getAddress(customer), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/address1")
    public ResponseEntity<String> getAddress1(@RequestBody Customer customer) {
        try {
            return new ResponseEntity<>(customerService.getAddress(customer), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/address2")
    public ResponseEntity<String> getAddress2(@RequestBody Customer customer) {
        try {
            return new ResponseEntity<>(customerService.getAddress(customer), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/address3")
    public ResponseEntity<String> getAddress3(@RequestBody Customer customer) {
        try {
            return new ResponseEntity<>(customerService.getAddress(customer), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/address4")
    public ResponseEntity<String> getAddress4(@RequestBody Customer customer) {
        try {
            return new ResponseEntity<>(customerService.getAddress(customer), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/address5")
    public ResponseEntity<String> getAddress5(@RequestBody Customer customer) {
        try {
            return new ResponseEntity<>(customerService.getAddress(customer), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
