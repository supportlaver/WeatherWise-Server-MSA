package com.idle.chattingservice.weatheralert.api.port;

import com.idle.chattingservice.weatheralert.api.response.WeatherAlertResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WeatherAlertService {

    Mono<Void> updateWeatherAlerts();
    Mono<Void> deactivateExpiredAlerts();
    Mono<Void> deleteOldDeactivatedAlerts(int day);
    Flux<WeatherAlertResponse> getAllWeatherAlerts();
    Flux<WeatherAlertResponse> getAllActivatedWeatherAlerts();
    Mono<WeatherAlertResponse> getWeatherAlertById(Long weatherAlertId);
}
