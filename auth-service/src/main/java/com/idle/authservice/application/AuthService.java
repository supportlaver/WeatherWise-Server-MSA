package com.idle.authservice.application;

import com.idle.authservice.application.dto.request.AuthSignUpRequest;
import com.idle.authservice.application.dto.response.AuthSignUpResponse;
import com.idle.authservice.infrastructure.UserServiceClient;
import com.idle.authservice.presentation.dto.request.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserServiceClient userClient;
    private final PasswordEncoder passwordEncoder;
    public AuthSignUpResponse signUp(AuthSignUpRequest req) {
        req.encryptionPassword(passwordEncoder.encode(req.getPassword()));
        return userClient.signUp(req);
    }
}
