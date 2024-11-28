package com.idle.missionservice.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentWeatherInfoRequest {
    private double lat;
    private double lon;

    public static CurrentWeatherInfoRequest of(double latitude, double longitude) {
        return CurrentWeatherInfoRequest.builder()
                .lat(latitude)
                .lon(longitude)
                .build();
    }
}
