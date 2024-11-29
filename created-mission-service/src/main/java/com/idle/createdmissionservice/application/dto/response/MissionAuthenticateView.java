package com.idle.createdmissionservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder @AllArgsConstructor
@Getter @NoArgsConstructor
public class MissionAuthenticateView {
    private boolean isAuthenticated;
    private int missionExp; // 해당 미션 포인트
    private int level; // 현재 user level
    private int exp; // 현재 user 가 보여하고 있는 포인트

    public static MissionAuthenticateView success(boolean isAuthenticated , int missionPoint , int userLevel ,
                                         int userPoint) {
        return MissionAuthenticateView.builder()
                .isAuthenticated(isAuthenticated)
                .missionExp(missionPoint)
                .level(userLevel)
                .exp(userPoint)
                .build();
    }

    public static MissionAuthenticateView fail() {
        return MissionAuthenticateView.builder()
                .isAuthenticated(false)
                .build();
    }
}
