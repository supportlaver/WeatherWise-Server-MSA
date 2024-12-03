package com.idle.boardservice.infrastructure;

import com.idle.boardservice.infrastructure.dto.response.UserResponse;
import com.idle.commonservice.annotation.UserId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * User 바운디드 컨텍스트와 통신할 Client
 */
@FeignClient(name = "user-service" , url = "http://localhost:8081/api/users")
public interface UserServiceClient {
    @GetMapping
    UserResponse findById(@UserId Long userId);
}
