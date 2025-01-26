package com.idle.chattingservice.message.api.port;

import com.idle.chattingservice.message.api.response.ChatLastReadMessageResponse;
import com.idle.chattingservice.message.api.response.ChatReadStatusResponse;
import reactor.core.publisher.Mono;

public interface ChatReadService {
    Mono<ChatReadStatusResponse> markAsRead(Long chatRoomId, Long userId, Long chatMessageId);
    Mono<ChatLastReadMessageResponse> getLastReadMessageId(Long chatRoomId, Long userId);
}
