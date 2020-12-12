package com.vengeance.authserver.dao;

import com.vengeance.authserver.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerDAO extends CrudRepository<Customer, Long> {

}
