package com.idle.authservice.infrastructure.auth.service;

import com.idle.authservice.infrastructure.UserServiceClient;
import com.idle.authservice.infrastructure.auth.CustomUserDetails;
import com.idle.authservice.presentation.dto.UserSecurityForm;
import com.idle.authservice.presentation.dto.UserSecurityFormDto;
import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    // private final UserJpaRepository userJpaRepository;
    private final UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserSecurityFormDto userDto = userServiceClient.findUserIdAndRoleBySerialId(username)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        log.info("user = {} ", userDto.getId());
        log.info("user = {} ", userDto.getRole());
        log.info("user = {} ", userDto.getPassword());

        // UserSecurityForm 인터페이스로 변환
        UserSecurityForm user = UserSecurityFormDto.toSecurityForm(userDto);

        return CustomUserDetails.create(user);
    }
    public UserDetails loadUserById(Long userId) throws BaseException {
        UserSecurityFormDto userDto = userServiceClient.findByIdAndIsLoginAndRefreshTokenIsNotNull(userId, true)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        log.info("user = {} " ,userDto.getId());
        log.info("user = {} " ,userDto.getRole());
        log.info("user = {} " ,userDto.getPassword());
        /*UserJpaRepository.UserSecurityForm user = userJpaRepository.findByIdAndIsLoginAndRefreshTokenIsNotNull(userId, true)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found UserId"));*/
        // UserSecurityForm 인터페이스로 변환
        UserSecurityForm user = UserSecurityFormDto.toSecurityForm(userDto);

        return CustomUserDetails.create(user);
    }
}