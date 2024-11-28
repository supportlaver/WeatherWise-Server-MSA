package com.idle.userservice.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 도메인 서비스
 * 닉네임 중복 체크 + 비밀번호 암호화
 */
@Service
@RequiredArgsConstructor
public class UserInfoCheckService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

}
