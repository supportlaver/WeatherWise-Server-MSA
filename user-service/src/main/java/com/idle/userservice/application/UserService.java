package com.idle.userservice.application;

import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.userservice.application.dto.request.UserRequest;
import com.idle.userservice.application.dto.request.UserSecurityFormDto;
import com.idle.userservice.application.dto.response.UserResponse;
import com.idle.userservice.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor @Slf4j
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserResponse findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        return UserResponse.from(user);
    }

    @Transactional
    public UserSecurityFormDto createUser(UserRequest req) {
        User user = userRepository.save(User.of(req.getSerialId() , req.getPassword() ,
                                                req.getProvider() , req.getRole() , req.getNickname()));
        return new UserSecurityFormDto(user.getId(), user.getPassword().getValue(), user.getRole());
    }
}
