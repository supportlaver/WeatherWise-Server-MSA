package com.idle.createdmissionservice.domain;

import com.idle.createdmissionservice.application.dto.response.MissionAuthenticateView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissionAuth {
    private String missionName;
    private String missionImageUrl;
    private String question;

    public static MissionAuth of(String missionName , String question , String missionImageUrl) {
        return MissionAuth.builder()
                .missionName(missionName)
                .question(question)
                .missionImageUrl(missionImageUrl)
                .build();
    }
}
