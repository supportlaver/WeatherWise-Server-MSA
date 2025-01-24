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
        WeatherData res = weatherProvider.getCurrentWeatherInfo(latitude, longitude);
        return WeatherInfo.from(res);
    }

}
