package com.tutorial.cache.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thanhvt
 * @project spring-cache-demo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

  private int id;
  private String name;
  private String address;
}
