package com.mongodb.models.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "role")
public class Role implements GrantedAuthority {

  @Id
  private String id;

  @Field
  @Indexed(unique = true)
  private String authority;

  public Role(String authority) {
    this.authority = authority;
  }
}
