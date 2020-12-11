package com.mongodb.repositories;

import com.mongodb.models.documents.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {

}
