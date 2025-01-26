package com.idle.chattingservice.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idle.chattingservice.kafka.producer.ChatMessageProducer;
import com.idle.chattingservice.kafka.request.KafkaChatMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class ChatWebSocketHandler implements WebSocketHandler {

    private final ChatMessageProducer kafkaProducer;
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(payload -> processMessage(payload, session))
                .then();
    }

    private Mono<Void> processMessage(String payload, WebSocketSession session) {
        return Mono.fromCallable(() -> objectMapper.readValue(payload, KafkaChatMessageRequest.class))
                .doOnNext(request -> {
                    kafkaProducer.sendMessage(request);
                    log.info("카프카 - 메시지 전송 성공: {}", request.message());
                })
                .flatMap(request -> session.send(Mono.just(session.textMessage("메시지 전송 성공: " + request.message()))))
                .doOnError(e -> log.error("카프카 - 메시지 처리 실패: {}", e.getMessage()))
                .onErrorResume(e -> Mono.empty()); // 에러 발생 시 Mono 빈 스트림 반환
    }
}
