package com.mongodb.models.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Document(collection = "verification_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {

  private static final int EXPIRATION = 60 * 24;

  @Id
  private String id;

  private String token;

  @DBRef
  private User user;

  private Date expiryDate;

  private static Date calculateExpiryDate() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Timestamp(cal.getTime().getTime()));
    cal.add(Calendar.MINUTE, EXPIRATION);
    return new Date(cal.getTime().getTime());
  }

  public VerificationToken(String token, User user) {
    this.token = token;
    this.user = user;
    this.expiryDate = VerificationToken.calculateExpiryDate();
  }
}
