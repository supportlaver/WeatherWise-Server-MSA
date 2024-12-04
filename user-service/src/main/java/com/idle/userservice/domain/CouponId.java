package com.idle.userservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class CouponId {
    @Column(name = "coupon_id")
    private Long couponId;
}
