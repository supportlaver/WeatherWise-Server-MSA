package com.idle.chattingservice.chatroom.api.port;


import com.idle.chattingservice.chatroom.api.response.ChatRoomResponse;
import com.idle.chattingservice.chatroom.repository.ReactiveChatRoomEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatRoomService {
    Mono<ReactiveChatRoomEntity> getOrCreateChatRoom(String parentRegionCode, String parentRegionName);
    Mono<Void> deleteChatRoomById(Long chatRoomId);
    Mono<Void> saveChatRoom(ReactiveChatRoomEntity chatRoom);
    Flux<ChatRoomResponse> getAllChatRooms();
    Flux<ChatRoomResponse> getAllActivatedChatRooms();
    Mono<Void> updateChatRoomName(Long chatRoomId);
    Mono<Void> deleteOldDeactivatedChatRooms(int day);
    Mono<ReactiveChatRoomEntity> getChatRoomById(Long chatRoomId);
}
