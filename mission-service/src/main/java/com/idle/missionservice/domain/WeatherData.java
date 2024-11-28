package com.idle.missionservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@AllArgsConstructor @NoArgsConstructor
@Getter
public class WeatherData {
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
}
