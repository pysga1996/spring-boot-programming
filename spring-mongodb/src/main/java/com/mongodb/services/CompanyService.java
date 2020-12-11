package com.mongodb.services;

import com.mongodb.models.documents.Company;

import java.util.List;

public interface CompanyService {

  List<Company> getCompanyList();

  void createCompany(Company company);

  void editCompany(Company company, String id);

  void deleteCompany(String id);
}
