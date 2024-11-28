package com.idle.userservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
@AllArgsConstructor @Builder
public class AuthSignUpRequest {
    private String loginId;
    private String nickName;
    private String password;
}
