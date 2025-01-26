package com.idle.chattingservice.websocket.config;

import com.idle.chattingservice.kafka.producer.ChatMessageProducer;
import com.idle.chattingservice.websocket.handler.ChatWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.Map;

@Slf4j
@Configuration
public class WebSocketConfig {

    @Bean
    public HandlerMapping webSocketHandlerMapping(ChatWebSocketHandler chatWebSocketHandler) {
        return new SimpleUrlHandlerMapping(Map.of("/ws/chat", chatWebSocketHandler), 1);
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public ChatWebSocketHandler chatWebSocketHandler(ChatMessageProducer chatMessageProducer) {
        return new ChatWebSocketHandler(chatMessageProducer);
    }

}
