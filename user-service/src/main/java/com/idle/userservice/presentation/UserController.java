package com.idle.userservice.presentation;

import com.idle.commonservice.annotation.UserId;
import com.idle.commonservice.base.BaseResponse;
import com.idle.userservice.application.UserService;
import com.idle.userservice.application.dto.request.AuthSignUpRequest;
import com.idle.userservice.application.dto.request.UserRequest;
import com.idle.userservice.application.dto.request.UserSecurityFormDto;
import com.idle.userservice.application.dto.response.AuthSignUpResponse;
import com.idle.userservice.application.dto.response.UserAcquisitionExpResponse;
import com.idle.userservice.application.dto.response.UserResponse;
import com.idle.userservice.presentation.dto.request.AcquisitionExp;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @GetMapping
    public UserResponse findById(@UserId Long userId) {
        return userService.findById(userId);
    }

    @GetMapping("/no-token/{user-id}")
    public ResponseEntity<UserResponse> findByIdNoToken(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok().body(userService.findById(userId));
    }

    @PostMapping
    public ResponseEntity<UserSecurityFormDto> createUser(@RequestBody UserRequest req) {
        UserSecurityFormDto res = userService.createUser(req);
        return ResponseEntity.ok().body(res);
    }

    // 자체 회원 가입
    @PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthSignUpResponse signUp(@RequestBody AuthSignUpRequest req) {
        return userService.signUp(req);
    }

    // 경험치 획득
    @PostMapping(path = "/exp")
    public UserAcquisitionExpResponse acquisitionExp(@RequestBody AcquisitionExp req) {
        return userService.acquisitionExp(req.getUserId() , req.getExp());
    }
}
