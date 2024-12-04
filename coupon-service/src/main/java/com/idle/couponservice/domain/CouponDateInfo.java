package com.idle.couponservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Builder @Getter
@AllArgsConstructor @NoArgsConstructor
public class CouponDateInfo {

    @Column(name = "coupon_issuedAt")
    private LocalDateTime issuedAt;

    @Column(name = "coupon_expiresAt")
    private LocalDateTime expiresAt;
}
