package com.mongodb.models.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "company")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

  @Id
  private String id;

  @Field
  @Indexed
  private String name;

  @Field
  private String location;

  @DBRef
  @Field(name = "departmentList")
  private List<Department> departments;
}
