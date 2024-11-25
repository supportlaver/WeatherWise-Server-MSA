package com.idle.authservice.presentation.dto.response;

import com.idle.commonservice.auth.EProvider;
import com.idle.commonservice.auth.ERole;
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
    private Boolean easilyHot;
    private Boolean easilyCold;
    private Boolean easilySweat;
    private Boolean isCompletedSurvey;
}
