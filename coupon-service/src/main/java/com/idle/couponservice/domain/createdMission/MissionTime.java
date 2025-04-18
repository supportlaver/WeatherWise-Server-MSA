package com.idle.couponservice.domain.createdMission;


import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;

public enum MissionTime {

    MORNING("아침"),
    AFTERNOON("점심"),
    EVENING("저녁");

    private final String displayName;

    MissionTime(String displayName) {
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
