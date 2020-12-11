package com.mongodb.models.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "department")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

  @Id
  private String id;

  @Field
  @Indexed
  private String name;

  @Field
  private Integer employeeCount;
}
