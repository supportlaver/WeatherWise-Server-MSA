package com.idle.checkservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyMissionVerifiedResultEvent extends Event {
    private Long userId;
    private Long couponId;
    private String correlationId;
    private boolean dailyMissionVerifiedResult;
}
