package com.stomp.controllers;

import com.stomp.models.Greeting;
import com.stomp.models.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {

    private final SimpMessageSendingOperations template;

    @Autowired
    public MessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(@Payload HelloMessage message, StompHeaderAccessor stompHeaderAccessor)
        throws Exception {
//        String host = stompHeaderAccessor.getHost();
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/to-self")
    @SendToUser("/queue/greetings")
    public Greeting messageToSelf(@Payload Greeting greeting) {
        greeting.setTo(greeting.getFrom());
        return greeting;
    }

    @MessageMapping("/to-someone")
    public void messageToUser(@Payload Greeting greeting, @Header("simpSessionId") String sessionId) {
        template.convertAndSendToUser(greeting.getTo(), "/queue/greetings", greeting);
    }
}
