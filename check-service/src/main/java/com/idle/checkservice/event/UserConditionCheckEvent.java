package com.idle.checkservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserConditionCheckEvent extends Event {
    private String correlationId;
    private Long userId;
    private Long couponId;
    private LocalDateTime localDateTime;
}
