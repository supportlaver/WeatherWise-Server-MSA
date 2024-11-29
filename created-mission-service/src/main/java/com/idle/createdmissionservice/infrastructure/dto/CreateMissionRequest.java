package com.idle.createdmissionservice.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMissionRequest {
    private double lat;
    private double lon;

    public static CreateMissionRequest of(double latitude, double longitude) {
        return CreateMissionRequest.builder()
                .lat(latitude)
                .lon(longitude)
                .build();
    }
}
