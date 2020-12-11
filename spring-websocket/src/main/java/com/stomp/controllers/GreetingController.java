package com.stomp.controllers;

import com.stomp.models.Greeting;
import com.stomp.models.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

@Controller
public class GreetingController {
//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public Greeting greeting(HelloMessage message, StompHeaderAccessor stompHeaderAccessor) throws Exception {
//        String host = stompHeaderAccessor.getHost();
//        Thread.sleep(1000); // simulated delay
//        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
//    }

  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public Greeting greeting() throws Exception {
    return new Greeting("Hello!" + new Date().getTime());
  }
}
