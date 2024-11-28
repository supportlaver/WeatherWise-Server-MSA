package com.idle.userservice.application;

import com.idle.commonservice.auth.EProvider;
import com.idle.commonservice.auth.ERole;
import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.userservice.application.dto.request.AuthSignUpRequest;
import com.idle.userservice.application.dto.request.UpdateUserRefreshToken;
import com.idle.userservice.application.dto.request.UserSecurityFormDto;
import com.idle.userservice.application.dto.response.AuthSignUpResponse;
import com.idle.userservice.domain.User;
import com.idle.userservice.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void updateRefreshTokenAndLoginStatus(UpdateUserRefreshToken req) {
        userRepository.updateRefreshTokenAndLoginStatus(req.getId() , req.getRefreshToken() , true);
    }

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

    // 트랜잭션은 application 레이어에서 관리
    // Entity (도메인) 에 관련된 기능들은 Entity 안에다가..
    // 닉네임 중복 체크가 다른 도메인에서 재사용성이 있는가?
    // Validation , Method 재사용성이 있는가?
    // Yes : Entity 에 넣는 것을 고려

    @Transactional
    public AuthSignUpResponse signUp(AuthSignUpRequest req) {
        // 닉네임 중복 체크
        userRepository.findByNicknameAndIsDeleted(req.getNickName(), false)
                .ifPresent(u -> {
                    throw new BaseException(ErrorCode.DUPLICATION_NICKNAME);
                });

        // User 생성 (passwordEncoder 사용)
        User user = userRepository.save(User.signUpSocialLogin(req.getLoginId(),
                passwordEncoder.encode(req.getPassword()), EProvider.DEFAULT, ERole.USER, req.getNickName()));

        return AuthSignUpResponse.of(user.getId() , user.getRole() ,
                user.getProvider() , user.getSerialId() , user.getNickname() , user.getCreatedAt());
    }
}
