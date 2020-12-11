package com.mongodb.listeners;

import com.mongodb.events.CustomEvent;
import com.mongodb.models.documents.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public abstract class CustomListener {

  private JavaMailSender mailSender;

  @Autowired
  public void setMailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  private Environment environment;

  @Autowired
  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }

  void sendMail(String emailSubject, String emailText, String param, String token, User user,
      CustomEvent event) {
    String host = environment.getProperty("BACKEND_HOST");
    String recipientAddress = user.getEmail();
    String confirmationUrl = event.getAppUrl() + param + token;
    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(recipientAddress);
    email.setSubject(emailSubject);
    email.setText(emailText.concat("\n").concat(host).concat(confirmationUrl).concat("&id=")
        .concat(user.getId()));
    mailSender.send(email);
  }
}
