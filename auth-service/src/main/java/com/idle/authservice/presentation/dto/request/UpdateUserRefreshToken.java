package com.idle.authservice.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
@AllArgsConstructor @Builder
public class UpdateUserRefreshToken {
    private Long id;
    private String refreshToken;

    public static UpdateUserRefreshToken from(Long id , String refreshToken) {
        return UpdateUserRefreshToken.builder()
                .id(id)
                .refreshToken(refreshToken)
                .build();
    }
}
