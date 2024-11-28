package com.idle.missionservice.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idle.missionservice.application.dto.response.WeatherInfo;
import com.idle.missionservice.domain.AIWeatherInfoProvider;
import com.idle.missionservice.domain.WeatherData;
import com.idle.missionservice.infrastructure.CurrentWeatherInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final AIWeatherInfoProvider weatherProvider;
    public WeatherInfo getCurrentWeatherInfo(double latitude , double longitude) {
        // 날씨 정보 가져오기
        WeatherData res = weatherProvider.getCurrentWeatherInfo(latitude, longitude);
        // application 계층 DTO 로 변환
        return WeatherInfo.from(res);
    }
}
