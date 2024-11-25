package com.idle.userservice.application;

import com.idle.commonservice.auth.EProvider;
import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.userservice.application.dto.request.UpdateUserRefreshToken;
import com.idle.userservice.application.dto.request.UserSecurityFormDto;
import com.idle.userservice.domain.User;
import com.idle.userservice.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userRepository;

    @Transactional
    public void updateRefreshTokenAndLoginStatus(UpdateUserRefreshToken req) {
        userRepository.updateRefreshTokenAndLoginStatus(req.getId() , req.getRefreshToken() , true);
    }

    @Transactional
    public void findBySerialIdAndProvider(String serialId, EProvider provider) {
        userRepository.findBySerialIdAndProvider(serialId,provider);
    }

    public UserSecurityFormDto findUserIdAndRoleBySerialId(String serialId) {
        User user = userRepository.findUserIdAndRoleBySerialId(serialId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        return new UserSecurityFormDto(user.getId() , user.getPassword().getValue() , user.getRole());
    }

    public UserSecurityFormDto findByIdAndIsLoginAndRefreshTokenIsNotNull(Long id , boolean b) {
        User user = userRepository.findByIdAndIsLoginAndRefreshTokenIsNotNull(id, b)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        return new UserSecurityFormDto(user.getId() , user.getPassword().getValue() , user.getRole());
    }

}
