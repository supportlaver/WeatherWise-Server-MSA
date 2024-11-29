package com.idle.authservice.application.dto.response;

import com.idle.commonservice.auth.EProvider;
import com.idle.commonservice.auth.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter @AllArgsConstructor @NoArgsConstructor
@Builder
public class AuthSignUpResponse {
    private Long userId;
    private ERole role;
    private EProvider provider;
    private String serialId;
    private String nickname;
    private LocalDateTime createdAt;

    public static AuthSignUpResponse of(Long userId, ERole role, EProvider provider,
                                        String serialId, String nickname, LocalDateTime createdAt) {
        return AuthSignUpResponse.builder()
                .userId(userId)
                .role(role)
                .provider(provider)
                .serialId(serialId)
                .nickname(nickname)
                .createdAt(createdAt)
                .build();
    }
}
