package com.idle.authservice.application;

import com.idle.authservice.application.dto.request.AuthSignUpRequest;
import com.idle.authservice.application.dto.response.AuthSignUpResponse;
import com.idle.authservice.infrastructure.UserServiceClient;
import com.idle.authservice.presentation.dto.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserServiceClient userClient;
    private final PasswordEncoder passwordEncoder;


    public AuthSignUpResponse signUp(AuthSignUpRequest req) {
        // 암호화
        req.encryptionPassword(passwordEncoder.encode(req.getPassword()));

        // 전송
        return userClient.signUp(req);
    }
}
