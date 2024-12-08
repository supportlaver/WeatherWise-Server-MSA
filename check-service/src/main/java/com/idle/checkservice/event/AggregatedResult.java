package com.idle.checkservice.event;

import lombok.Data;
import lombok.ToString;

@Data @ToString
public class AggregatedResult {
    private Long userId;
    private Long couponId;
    private String correlationId;
    private boolean userCheckResult;
    private boolean dailyMissionVerifiedResult;

    // Constructors, Getters, Setters
    public AggregatedResult(Long userId) {
        this.userId = userId;
    }

    public AggregatedResult(String correlationId, Long userId , Long couponId) {
        this.correlationId = correlationId;
        this.userId = userId;
        this.couponId = couponId;
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