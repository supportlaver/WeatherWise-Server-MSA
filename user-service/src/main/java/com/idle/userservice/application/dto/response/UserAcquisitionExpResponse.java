package com.idle.userservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAcquisitionExpResponse {
    private Long userId;
    private int userLevel;
    private String nickName;
    private int exp;

    public static UserAcquisitionExpResponse of(Long userId , int userLevel , int exp , String nickName) {
        return UserAcquisitionExpResponse.builder()
                .userId(userId)
                .userLevel(userLevel)
                .exp(exp)
                .nickName(nickName)
                .build();
    }
}
