package com.idle.userservice.presentation;

import com.idle.commonservice.base.BaseResponse;
import com.idle.userservice.application.UserService;
import com.idle.userservice.presentation.dto.auth.request.UserRequest;
import com.idle.userservice.presentation.dto.auth.response.UserResponse;
import com.idle.userservice.presentation.dto.auth.response.UserSecurityFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    @GetMapping("/{user-id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok().body(userService.findById(userId));
    }

    @PostMapping
    public ResponseEntity<UserSecurityFormDto> createUser(@RequestBody UserRequest req) {
        UserSecurityFormDto res = userService.createUser(req);
        return ResponseEntity.ok().body(res);
    }

}
