package com.idle.authservice.infrastructure;

import com.idle.authservice.presentation.dto.UserSecurityForm;
import com.idle.authservice.presentation.dto.UserSecurityFormDto;
import com.idle.authservice.presentation.dto.request.UpdateUserRefreshToken;
import com.idle.authservice.presentation.dto.request.UserRequest;
import com.idle.authservice.presentation.dto.response.UserResponse;
import com.idle.commonservice.auth.EProvider;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * User 바운디드 컨텍스트와 통신할 Client
 */
@FeignClient(name = "user-service" , url = "http://localhost:8081/api")
public interface UserServiceClient {
    @PostMapping("/user-auth/refresh-token")
    void updateRefreshTokenAndLoginStatus(UpdateUserRefreshToken req);

    @GetMapping("/user/{user-id}")
    UserResponse findById(@PathVariable("user-id") Long userId);


    @GetMapping("/user-auth/serial-id-provider")
    Optional<UserSecurityFormDto> findBySerialIdAndProvider(
            @RequestParam("serialId") String serialId,
            @RequestParam("provider") EProvider provider
    );

    @PostMapping("/user")
    UserSecurityFormDto createUser(@RequestBody UserRequest userRequest);

    @GetMapping("/user-auth/user-id-role")
    Optional<UserSecurityFormDto> findUserIdAndRoleBySerialId(@RequestParam("serial-id") String serialId);

    @GetMapping("/user-auth/login-refresh-token")
    Optional<UserSecurityFormDto> findByIdAndIsLoginAndRefreshTokenIsNotNull(@RequestParam("user-id") Long id ,
                                                                          @RequestParam("refresh-token-null") boolean b);
}
