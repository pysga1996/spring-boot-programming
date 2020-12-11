package com.mongodb.controllers;

import com.mongodb.models.documents.Department;
import com.mongodb.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

  private DepartmentService departmentService;

  @Autowired
  public void setDepartmentService(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @GetMapping("/list")
  public ResponseEntity<List<Department>> getDepartmentList() {
    try {
      return new ResponseEntity<>(departmentService.getDepartmentList(), HttpStatus.OK);
    } catch (Exception ex) {
      ex.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  public ResponseEntity<Void> createDepartment(@RequestBody Department department) {
    try {
      departmentService.createDepartment(department);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      ex.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping
  public ResponseEntity<Void> editDepartment(@RequestParam("id") String departmentId,
      @RequestBody Department department) {
    try {
      departmentService.editDepartment(department, departmentId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      ex.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteDepartment(@RequestParam("id") String departmentId) {
    try {
      departmentService.deleteDepartment(departmentId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      ex.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
