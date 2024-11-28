package com.idle.missionservice.domain;


import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;

public enum WeatherType {
    SUNNY("화창한 날씨") ,
    COLD("추운 날씨"),
    RAINY("비 오는 날씨"),
    SNOWY("눈 오는 날씨"),
    COOL("시원한 날씨"),
    WINDY("바람이 강한 날씨");

    private final String displayName;

    WeatherType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}
