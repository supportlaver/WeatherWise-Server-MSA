package com.idle.userservice.application;

import com.idle.commonservice.auth.EProvider;
import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.userservice.domain.Password;
import com.idle.userservice.domain.UserRepository;
import com.idle.userservice.presentation.dto.auth.request.UpdateUserRefreshToken;
import com.idle.userservice.presentation.dto.auth.response.UserSecurityFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import static com.idle.userservice.domain.UserRepository.*;

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
        UserSecurityForm form = userRepository.findUserIdAndRoleBySerialId(serialId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        return new UserSecurityFormDto(form.getId() , form.getPassword() , form.getRole());
    }

    public UserSecurityFormDto findByIdAndIsLoginAndRefreshTokenIsNotNull(Long id , boolean b) {
        UserSecurityForm form = userRepository.findByIdAndIsLoginAndRefreshTokenIsNotNull(id,b)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
        return new UserSecurityFormDto(form.getId() , form.getPassword() , form.getRole());
    }

}
