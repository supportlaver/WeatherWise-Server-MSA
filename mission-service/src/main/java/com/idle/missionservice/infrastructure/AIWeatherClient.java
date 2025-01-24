package com.idle.missionservice.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.missionservice.domain.AIWeatherInfoProvider;
import com.idle.missionservice.domain.WeatherData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class AIWeatherClient implements AIWeatherInfoProvider {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${ai.endpoints.weather}")
    private String aiWeatherEndpoints;

    private static final String AI_LOCAL_ENDPOINT = "http://localhost:8000/";
    @Override
    public WeatherData getCurrentWeatherInfo(double latitude, double longitude) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CurrentWeatherInfoRequest weatherRequest = CurrentWeatherInfoRequest.of(latitude, longitude);
        HttpEntity<CurrentWeatherInfoRequest> request = new HttpEntity<>(weatherRequest, headers);

        try {
            String response = restTemplate.postForObject(AI_LOCAL_ENDPOINT + "weather_data", request, String.class);
            CurrentWeatherInfoResponse res = objectMapper.readValue(response, CurrentWeatherInfoResponse.class);
            return convertToDomainModel(res);
        } catch (JsonProcessingException e) {
            throw new BaseException(ErrorCode.CONVERT_JSON_ERROR);
        }
    }

    private WeatherData convertToDomainModel(CurrentWeatherInfoResponse res) {
        return new WeatherData(
                res.getCurrentTemperature(),
                res.getMinimumTemperature(),
                res.getMaximumTemperature(),
                res.getSkyCondition(),
                res.getPrecipitationType(),
                res.isRained(),
                res.isSnowed(),
                res.getPrecipitationAmount(),
                res.getSnowfallAmount(),
                res.getAiMessage()
        );
    }

}
