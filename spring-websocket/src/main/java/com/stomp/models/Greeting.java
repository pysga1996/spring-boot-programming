package com.stomp.models;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Greeting {

  public Greeting(String content) {
    this.content = content;
  }

  public Greeting(String content, Date timestamp) {
    this.content = content;
    this.timestamp = timestamp;
  }

  private String content;

  private Date timestamp;

  private String from;

  private String to;
}
