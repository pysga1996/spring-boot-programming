package com.stomp.configurations;

import com.stomp.models.Greeting;
import org.springframework.stereotype.Component;

@Component
public class MessagesSupplier {

  public Greeting get() {
    return new Greeting("Hello " + Math.random());
  }
}
