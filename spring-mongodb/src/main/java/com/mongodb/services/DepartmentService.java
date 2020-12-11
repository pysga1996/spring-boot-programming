package com.mongodb.services;

import com.mongodb.models.documents.Department;

import java.util.List;

public interface DepartmentService {

  List<Department> getDepartmentList();

  void createDepartment(Department department);

  void editDepartment(Department department, String id);

  void deleteDepartment(String id);
}
