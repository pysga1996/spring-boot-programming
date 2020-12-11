package com.mongodb.services.impl;

import com.mongodb.models.documents.Company;
import com.mongodb.repositories.CompanyRepository;
import com.mongodb.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

  private CompanyRepository companyRepository;

  @Autowired
  public void setCompanyRepository(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  @Override
  public List<Company> getCompanyList() {
    return companyRepository.findAll();
  }

  @Override
  public void createCompany(Company company) {
    companyRepository.save(company);
  }

  @Override
  public void editCompany(Company company, String id) {
    companyRepository.save(company);
  }

  @Override
  public void deleteCompany(String id) {
    companyRepository.deleteById(id);
  }
}
