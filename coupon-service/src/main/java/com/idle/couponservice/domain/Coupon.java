package com.idle.couponservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Coupon {
    @Id @Column(name = "coupon_id")
    private Long id;

    @Embedded
    private CouponDateInfo couponDateInfo;

    @Embedded
    private CouponConditionInfo couponConditionInfo;

    @Embedded
    private CouponOwner couponOwner;

    @Column(name = "coupon_name")
    private String name;

    @Column(name = "coupo_is_active")
    private boolean isActive;
}
