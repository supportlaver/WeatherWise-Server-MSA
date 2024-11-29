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
@RequestMapping("/api")
public class WeatherInfoController {

    private final WeatherService weatherService;

    /**
     * 한 API 에서 멀티 스레드를 써서 가지고 오는게 더 빠를까 ?
     * 다른 API 로 구현해서 가지고 오는게 더 빠를까?
     * 테스트 해보기
     */
    @GetMapping("/weather")
    public ResponseEntity<BaseResponse<WeatherInfo>> getWeatherInfo(@RequestParam("latitude") double latitude,
                                                                    @RequestParam("longitude") double longitude){
        return ResponseEntity.ok().body(new BaseResponse<>(weatherService.getCurrentWeatherInfo(latitude,longitude)));
    }

    @GetMapping("/personalized-weather")
    public void getPersonalizedWeatherInfo(double latitude , double longitude,Long userId)  {
        weatherService.getPersonalizedWeatherInfo(latitude,longitude,userId);
    }
}
