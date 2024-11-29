package com.idle.createdmissionservice.application.dto.response;

import com.idle.createdmissionservice.domain.CreatedMission;
import com.idle.createdmissionservice.infrastructure.dto.response.MissionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder @Getter
@AllArgsConstructor @NoArgsConstructor
public class CreatedMissionsView {
    private Long createdMissionId;
    private String missionName;
    private int point;
    private String nickName;

    public static CreatedMissionsView of(CreatedMission cm , MissionResponse m , UserData u) {
        return CreatedMissionsView.builder()
                .createdMissionId(cm.getId())
                .missionName(m.getName())
                .point(m.getPoint())
                .nickName(u.getNickName())
                .build();
    }

}
