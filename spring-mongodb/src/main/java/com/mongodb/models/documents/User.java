package com.mongodb.models.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "user")
public class User implements UserDetails {

  @Id
  private String id;

  @Field
  @Indexed(unique = true)
  private String username;

  @Field
  private String password;

  @Field
  @DBRef
  private Collection<Role> authorities;

  @Field
  private boolean accountNonExpired = true;

  @Field
  private boolean accountNonLocked = true;

  @Field
  private boolean credentialsNonExpired = true;

  @Field
  private boolean enabled = true;

  @Field
  private String name;

  @Field
  private String avatar;

  @Field
  private String email;

  @Field
  @DBRef
  private Collection<NavigationItem> navigationItemCollection;

  public User(String username, String password, Collection<Role> authorities) {
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }
}
