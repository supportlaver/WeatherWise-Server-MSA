package com.idle.couponservice.infrastruture.dto.response;

import lombok.*;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserCompletedAnyMissionResponse {
    private boolean hasCompletedAnyMission;
}
