package com.idle.authservice.presentation;

import com.idle.authservice.application.AuthService;
import com.idle.authservice.application.dto.request.AuthSignUpRequest;
import com.idle.authservice.application.dto.response.AuthSignUpResponse;
import com.idle.authservice.presentation.dto.UserSecurityFormDto;
import com.idle.authservice.presentation.dto.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthSignUpResponse> createUser(@RequestBody AuthSignUpRequest req) {
        AuthSignUpResponse res = authService.signUp(req);
        return ResponseEntity.ok().body(res);
    }
}
