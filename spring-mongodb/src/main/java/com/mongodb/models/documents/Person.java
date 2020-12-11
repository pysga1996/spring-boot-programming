package com.mongodb.models.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "family")
public class Person {

  @Transient
  public static final String SEQUENCE_NAME = "people_sequence";

  @Id
  private String id;

  @Field
  @Indexed(unique = true)
  private String name;

  @Field
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date birthDate;

  @Field
  private Integer salary;

  @DBRef
//    @CascadeSave
//    @JsonSerialize(using = CustomPersonJsonSerializer.class)
  private Person dad;

  @DBRef
//    @CascadeSave
//    @JsonSerialize(using = CustomPersonJsonSerializer.class)
  private Person mom;

  @DBRef
  private List<Person> children;
}
