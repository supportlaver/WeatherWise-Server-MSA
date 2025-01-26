package com.idle.missionservice.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idle.missionservice.infrastructure.CurrentWeatherInfoResponse;

public interface AIWeatherInfoProvider {
    WeatherData getCurrentWeatherInfo(double latitude , double longitude);
}
