package com.tutorial.cache.services;

import com.tutorial.cache.models.Customer;

/**
 * @author thanhvt
 * @project spring-cache-demo
 */
public interface CustomerService {

  String getAddress(Customer customer) throws InterruptedException;

  String getAddress1(Customer customer) throws InterruptedException;

  String getAddress2(Customer customer) throws InterruptedException;

  String getAddress3(Customer customer) throws InterruptedException;

  String getAddress4(Customer customer) throws InterruptedException;

  String getAddress5(Customer customer) throws InterruptedException;
}
