package com.idle.userservice.application;

import com.idle.commonservice.auth.EProvider;
import com.idle.commonservice.auth.ERole;
import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.userservice.application.dto.request.AuthSignUpRequest;
import com.idle.userservice.application.dto.request.UserRequest;
import com.idle.userservice.application.dto.request.UserSecurityFormDto;
import com.idle.userservice.application.dto.response.AuthSignUpResponse;
import com.idle.userservice.application.dto.response.UserAcquisitionExpResponse;
import com.idle.userservice.application.dto.response.UserResponse;
import com.idle.userservice.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        User user = userRepository.save(User.signUpSocialLogin(req.getSerialId() , req.getPassword() ,
                                                req.getProvider() , req.getRole() , req.getNickname()));
        return new UserSecurityFormDto(user.getId(), user.getPassword().getValue(), user.getRole());
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

        // User 생성
        User user = userRepository.save(User.signUpSocialLogin(req.getLoginId(),req.getPassword(),
                EProvider.DEFAULT, ERole.USER, req.getNickName()));

        log.info("user Level = {} " , user.getLevel().getValue());

        return AuthSignUpResponse.of(user.getId() , user.getRole() ,
                user.getProvider() , user.getSerialId() , user.getNickname() , user.getCreatedAt());
    }

    @Transactional
    public UserAcquisitionExpResponse acquisitionExp(Long userId , int exp) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        // 경험치 획득 로직
        user.acquisitionExp(exp);
        return UserAcquisitionExpResponse.of(user.getId() , user.getLevel().getValue() ,
                user.getExp().getValue() , user.getNickname());
    }
}
