package com.idle.createdmissionservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMission {
    // 예보지점의 X 좌표값
    private double latitude;
    // 예보지점의 Y 좌표값
    private double longitude;
    // 미션 타임
    private String missionTime;
}
