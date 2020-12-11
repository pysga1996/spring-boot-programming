package com.mongodb.services.impl;

import com.mongodb.models.documents.Department;
import com.mongodb.repositories.DepartmentRepository;
import com.mongodb.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

  private DepartmentRepository departmentRepository;

  @Autowired
  public void setDepartmentRepository(DepartmentRepository departmentRepository) {
    this.departmentRepository = departmentRepository;
  }

  @Override
  public List<Department> getDepartmentList() {
    return departmentRepository.findAll();
  }

  @Override
  public void createDepartment(Department department) {
    departmentRepository.save(department);
  }

  @Override
  public void editDepartment(Department department, String id) {
    departmentRepository.save(department);
  }

  @Override
  public void deleteDepartment(String id) {
    departmentRepository.deleteById(id);
  }
}
