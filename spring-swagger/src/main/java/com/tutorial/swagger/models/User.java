package com.tutorial.swagger.models;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    @Column(name = "account_non_expired", nullable = false)
    private Boolean accountNonExpired;

    @Column(name = "account_non_locked", nullable = false)
    private Boolean accountNonLocked;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "birth_date")
    @PastOrPresent(message = "Birthdate must be in the past or at present")
    private Timestamp birthDate;

    @Column(name = "credentials_non_expired", nullable = false)
    private Boolean credentialsNonExpired;

    @Column(name = "display_name")
    @NotBlank(message = "First Name cannot be null")
    private String displayName;

    @Column(name = "email")
    @Email(regexp = ".@.\\..*", message = "Must be a valid email")
    private String email;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "gender")
    @NotNull(message = "Gender cannot be null")
    private Boolean gender;

    @Column(name = "password")
    @Pattern(regexp = "[a-zA-Z0-9]{8,16}")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "username")
    @Size(min = 8, max = 15, message = "Username length must be from 8 to 15")
    private String username;


}