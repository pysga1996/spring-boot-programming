package com.tutorial.batch.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thanhvt
 * @project spring-batch-demo
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

  private String lastName;
  private String firstName;
}
