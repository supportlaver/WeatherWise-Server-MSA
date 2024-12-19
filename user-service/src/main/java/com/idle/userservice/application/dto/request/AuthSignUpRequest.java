package com.idle.userservice.application.dto.request;

import lombok.*;

@Getter @NoArgsConstructor @Setter
@AllArgsConstructor @Builder
public class AuthSignUpRequest {
    private String loginId;
    private String nickName;
    private String password;
}
