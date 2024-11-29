package com.idle.createdmissionservice.infrastructure;

import com.idle.createdmissionservice.infrastructure.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * User 바운디드 컨텍스트와 통신할 Client
 */
@FeignClient(name = "user-service" , url = "http://localhost:8081/api")
public interface UserServiceClient {
    @GetMapping("/user/{user-id}")
    UserResponse findById(@PathVariable("user-id") Long userId);
}
