package com.idle.missionservice.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idle.commonservice.base.BaseResponse;
import com.idle.missionservice.application.WeatherService;
import com.idle.missionservice.application.dto.response.WeatherInfo;
import com.idle.missionservice.infrastructure.CurrentWeatherInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather")
public class WeatherInfoController {

    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<BaseResponse<WeatherInfo>> getWeatherInfo(@RequestParam("latitude") double latitude,
                                                                    @RequestParam("longitude") double longitude) throws JsonProcessingException {
        return ResponseEntity.ok().body(new BaseResponse<>(weatherService.getCurrentWeatherInfo(latitude,longitude)));

    }
}
