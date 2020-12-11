package com.stomp.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.stomp")
public class WebsocketApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebsocketApplication.class, args);
  }

}
