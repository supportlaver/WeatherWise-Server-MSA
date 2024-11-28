package com.idle.missionservice.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.idle.missionservice.domain.WeatherData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class WeatherInfo {
    @JsonProperty("Current_Temperature")
    private String currentTemperature;

    @JsonProperty("Minimum_Temperature")
    private String minimumTemperature;

    @JsonProperty("Maximum_Temperature")
    private String maximumTemperature;

    @JsonProperty("Sky_Condition")
    private String skyCondition;

    @JsonProperty("Precipitation_Type")
    private String precipitationType;

    @JsonProperty("Is_Rained")
    private boolean isRained;

    @JsonProperty("Is_Snowed")
    private boolean isSnowed;

    @JsonProperty("Precipitation_Amount")
    private String precipitationAmount;

    @JsonProperty("Snowfall_Amount")
    private String snowfallAmount;

    @JsonProperty("AI_message")
    private String aiMessage;

    public static WeatherInfo from(WeatherData data) {
        return WeatherInfo.builder()
                .aiMessage(data.getAiMessage())
                .currentTemperature(data.getCurrentTemperature())
                .isRained(data.isRained())
                .isSnowed(data.isSnowed())
                .maximumTemperature(data.getMaximumTemperature())
                .minimumTemperature(data.getMinimumTemperature())
                .precipitationAmount(data.getPrecipitationAmount())
                .skyCondition(data.getSkyCondition())
                .snowfallAmount(data.getSnowfallAmount())
                .build();
    }
}
