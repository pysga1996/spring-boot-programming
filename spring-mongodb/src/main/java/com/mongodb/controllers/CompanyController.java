package com.mongodb.controllers;

import com.mongodb.models.documents.Company;
import com.mongodb.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

  private CompanyService companyService;

  @Autowired
  public void setCompanyService(CompanyService companyService) {
    this.companyService = companyService;
  }

  @GetMapping("/list")
  public ResponseEntity<List<Company>> getCompanyList() {
    try {
      return new ResponseEntity<>(companyService.getCompanyList(), HttpStatus.OK);
    } catch (Exception ex) {
      ex.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  public ResponseEntity<Void> createCompany(@RequestBody Company company) {
    try {
      companyService.createCompany(company);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      ex.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping
  public ResponseEntity<Void> editCompany(@RequestParam("id") String companyId,
      @RequestBody Company company) {
    try {
      companyService.editCompany(company, companyId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      ex.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteCompany(@RequestParam("id") String companyId) {
    try {
      companyService.deleteCompany(companyId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      ex.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
