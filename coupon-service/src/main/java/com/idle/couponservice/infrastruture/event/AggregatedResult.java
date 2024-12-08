package com.idle.couponservice.infrastruture.event;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data @ToString
public class AggregatedResult {
    private Long userId;
    private boolean userCheckResult;
    private boolean dailyMissionVerifiedResult;

    // Constructors, Getters, Setters
    public AggregatedResult(Long userId) {
        this.userId = userId;
    }

    public void setUserCheckResult(boolean userCheckResult) {
        this.userCheckResult = userCheckResult;
    }

    public void setDailyMissionVerifiedResult(boolean dailyMissionVerifiedResult) {
        this.dailyMissionVerifiedResult = dailyMissionVerifiedResult;
    }

    public boolean isAllConditionsMet() {
        return !userCheckResult && dailyMissionVerifiedResult;
    }
}