package vn.vissoft.ems.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  @JsonIgnore
  @Column(name = "password")
  private String password;

  @Column(name = "user_name")
  private String userName;

  @Column(name = "email")
  private String email;
  @Column(name = "code")
  private String code;
  @Column(name = "is_locked")
  private boolean locked;
  @Column(name = "created_at")
  @CreationTimestamp
  private Date createdAt;
  @Column(name = "updated_at")
  @UpdateTimestamp
  private Date updatedAt;
}
