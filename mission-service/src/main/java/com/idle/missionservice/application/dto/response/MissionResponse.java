package com.idle.missionservice.application.dto.response;

import com.idle.missionservice.domain.Mission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissionResponse {
    private Long missionId;
    private String name;
    private int point;

    public static MissionResponse from(Mission m) {
        return MissionResponse.builder()
                .missionId(m.getId())
                .name(m.getName())
                .point(m.getRewardExp().getValue())
                .build();
    }
}
