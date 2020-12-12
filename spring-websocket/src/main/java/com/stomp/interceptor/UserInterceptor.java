package com.stomp.interceptor;

import com.stomp.models.StompPrincipal;
import java.util.LinkedList;
import java.util.Map;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

public class UserInterceptor implements ChannelInterceptor {

    @Override
    @SuppressWarnings("unchecked")
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor =
            MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) return message;
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object raw = message
                .getHeaders()
                .get(SimpMessageHeaderAccessor.NATIVE_HEADERS);

            if (raw instanceof Map) {
                Object userObject = ((Map<String, Object>) raw).get("login");

                if (userObject instanceof LinkedList) {
                    String username = ((LinkedList<Object>) userObject).get(0).toString();
                    accessor.setUser(new StompPrincipal(username));
                }
            }
        }
        return message;
    }
}
