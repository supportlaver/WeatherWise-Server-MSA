package com.idle.userservice.infrastructure.event;

import com.idle.commonservice.event.Event;
import lombok.*;

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
