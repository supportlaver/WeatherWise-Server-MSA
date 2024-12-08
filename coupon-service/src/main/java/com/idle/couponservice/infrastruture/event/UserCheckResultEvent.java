package com.idle.couponservice.infrastruture.event;

import com.idle.commonservice.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCheckResultEvent extends Event {
    private Long userId;
    private Long couponId;
    private String correlationId;
    private boolean userCheckResult;
}
