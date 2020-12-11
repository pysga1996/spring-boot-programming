package com.tutorial.cache.services.impl;

import com.tutorial.cache.models.Customer;
import com.tutorial.cache.services.CustomerService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * @author thanhvt
 * @project spring-cache-demo
 */
@Service
public class CustomerServiceImpl implements CustomerService {

  @Override
  @Cacheable(value = "addresses", key = "#customer.name")
  public String getAddress(Customer customer) throws InterruptedException {
    Thread.sleep(3000);
    return customer.getAddress();
  }

  @Override
  @Cacheable({"addresses", "directory"})
  public String getAddress1(Customer customer) throws InterruptedException {
    Thread.sleep(3000);
    return customer.getAddress();
  }

  @Override
  @CacheEvict(value = "addresses", allEntries = true)
  public String getAddress2(Customer customer) throws InterruptedException {
    Thread.sleep(3000);
    return customer.getAddress();
  }

  @Override
  @Caching(evict = {@CacheEvict("addresses"),
      @CacheEvict(value = "directory", key = "#customer.name")})
  public String getAddress3(Customer customer) throws InterruptedException {
    Thread.sleep(3000);
    return customer.getAddress();
  }

  @Override
  @CachePut(value = "addresses", condition = "#customer.name=='Tom'")
  public String getAddress4(Customer customer) throws InterruptedException {
    Thread.sleep(3000);
    return customer.getAddress();
  }

  @Override
  @CachePut(value = "addresses", condition = "#customer.name=='Tom'")
  // @CachePut(value = "addresses", unless = "#result.length>64")
  public String getAddress5(Customer customer) throws InterruptedException {
    Thread.sleep(3000);
    return customer.getAddress();
  }
}
