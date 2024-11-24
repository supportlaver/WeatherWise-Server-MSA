package com.idle.missionservice.domain;

import com.idle.weather.createdmission.domain.MissionTime;
import com.idle.weather.global.exception.BaseException;
import com.idle.weather.global.exception.ErrorCode;

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

    public static MissionTime fromValue(String missionTime) {
        for (MissionTime time : MissionTime.values()) {
            if (time.name().equalsIgnoreCase(missionTime)) {
                return time;
            }
        }
        throw new BaseException(ErrorCode.INVALID_MISSION_TIME);
    }

}
