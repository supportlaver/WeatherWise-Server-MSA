package com.idle.authservice.infrastructure.authentication.service;


import com.idle.authservice.infrastructure.UserServiceClient;
import com.idle.authservice.infrastructure.authentication.CustomUserDetails;
import com.idle.authservice.infrastructure.authentication.util.PasswordUtil;
import com.idle.authservice.infrastructure.info.OAuth2UserInfo;
import com.idle.authservice.infrastructure.info.OAuth2UserInfoFactory;
import com.idle.authservice.presentation.dto.UserSecurityForm;
import com.idle.authservice.presentation.dto.UserSecurityFormDto;
import com.idle.authservice.presentation.dto.request.UserRequest;
import com.idle.commonservice.auth.EProvider;
import com.idle.commonservice.auth.ERole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service @Slf4j
// @Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    // private final UserJpaRepository userJpaRepository;
    private final UserServiceClient userServiceClient;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            return this.process(userRequest, super.loadUser(userRequest));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public OAuth2User process(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
        EProvider provider = EProvider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, oauth2User.getAttributes());

        // user-service에서 사용자 정보 조회
        Optional<UserSecurityFormDto> userOptDto = userServiceClient.findBySerialIdAndProvider(userInfo.getId(), provider);

        // 사용자 정보가 없으면 새 사용자 생성
        UserSecurityFormDto userSecurityFormDto = userOptDto
                .orElseGet(() -> {
                    UserRequest userRequestDto = new UserRequest(
                            userInfo.getId(),
                            bCryptPasswordEncoder.encode(PasswordUtil.generateRandomPassword()),
                            provider,
                            ERole.USER,
                            userInfo.getNickName()
                    );
                    UserSecurityFormDto newUserDto = userServiceClient.createUser(userRequestDto);
                    log.info("CIAN dto = {} ", newUserDto.getPassword());
                    log.info("CIAN dto = {} ", newUserDto.getRole());
                    log.info("CIAN dto = {} ", newUserDto.getId());
                    return newUserDto;
                });

        log.info("JIWON dto = {} ", userSecurityFormDto.getPassword());
        log.info("JIWON dto = {} ", userSecurityFormDto.getRole());
        log.info("CIJIWONAN dto = {} ", userSecurityFormDto.getId());

        // UserSecurityForm 인터페이스로 변환
        UserSecurityForm userSecurityForm = UserSecurityFormDto.toSecurityForm(userSecurityFormDto);

        return CustomUserDetails.create(userSecurityForm, userInfo.getAttributes());

    }
}
