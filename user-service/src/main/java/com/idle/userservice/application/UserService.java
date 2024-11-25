package com.idle.userservice.application;

import com.idle.commonservice.base.BaseResponse;
import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.userservice.domain.Password;
import com.idle.userservice.domain.User;
import com.idle.userservice.domain.UserRepository;
import com.idle.userservice.presentation.dto.auth.request.UserRequest;
import com.idle.userservice.presentation.dto.auth.response.UserResponse;
import com.idle.userservice.presentation.dto.auth.response.UserSecurityFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor @Slf4j
@Service @Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserResponse findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        return UserResponse.from(user);
    }

    @Transactional
    public UserSecurityFormDto createUser(UserRequest req) {
        User user = userRepository.save(User.from(req));
        UserRepository.UserSecurityForm form = UserRepository.UserSecurityForm.invoke(user);
        log.info("user role = {} " , user.getRole());
        log.info("user role = {} " , user.getRole().getRoleCode());
        return new UserSecurityFormDto(form.getId(), form.getPassword(), form.getRole());
    }
}
