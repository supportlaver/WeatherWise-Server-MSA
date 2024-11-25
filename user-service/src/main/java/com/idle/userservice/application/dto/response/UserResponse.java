package com.idle.userservice.application.dto.response;

import com.idle.commonservice.auth.EProvider;
import com.idle.commonservice.auth.ERole;
import com.idle.userservice.domain.PersonalWeatherTraits;
import com.idle.userservice.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder @NoArgsConstructor @AllArgsConstructor
@Getter
public class UserResponse {
    private Long id;
    private String serialId;
    private String password;
    private EProvider provider;
    private ERole role;
    private String nickname;
    private Boolean isLogin;
    private String refreshToken;
    private Boolean isDeleted;
    private int level;
    private int point;
    private PersonalWeatherTraits personalWeatherTraits;
    private Boolean isCompletedSurvey;

    /**
     * 회원가입용
     */
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .serialId(user.getSerialId())
                .password(user.getPassword().getValue())
                .provider(user.getProvider())
                .role(user.getRole())
                .nickname(user.getNickname())
                .refreshToken(user.getRefreshToken())
                .isDeleted(user.getIsDeleted())
                .level(user.getLevel().getValue())
                .point(user.getPoint().getValue())
                .personalWeatherTraits(user.getPersonalWeatherTraits())
                .isCompletedSurvey(user.getIsCompletedSurvey())
                .build();
    }
}
