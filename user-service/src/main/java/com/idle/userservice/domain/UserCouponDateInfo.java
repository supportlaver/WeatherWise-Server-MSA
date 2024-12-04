package com.idle.userservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class UserCouponDateInfo {
    @Column(name = "userd_at")
    private LocalDateTime usedAt; // 사용 날짜

    @Column(name = "acquired_at")
    private LocalDateTime acquiredAt; // 획득 날짜

    public static UserCouponDateInfo issuedCoupon() {
        return UserCouponDateInfo.builder()
                .acquiredAt(LocalDateTime.now())
                .build();
    }
}
