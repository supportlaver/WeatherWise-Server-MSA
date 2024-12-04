package com.idle.couponservice.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;

@Embeddable
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class CouponConditionInfo {
    @Enumerated(STRING)
    private DiscountType discountType;
    private int discountValue;
}
