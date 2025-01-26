package com.idle.couponservice.domain.user;

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
    private LocalDateTime usedAt;
    @Column(name = "acquired_at")
    private LocalDateTime acquiredAt;

    public static UserCouponDateInfo issuedCoupon() {
        return UserCouponDateInfo.builder()
                .acquiredAt(LocalDateTime.now())
                .build();
    }
}
