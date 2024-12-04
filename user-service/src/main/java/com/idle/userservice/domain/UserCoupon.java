package com.idle.userservice.domain;

import com.idle.commonservice.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
public class UserCoupon extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_coupon_id")
    private Long id;

    @Embedded
    private CouponId couponId;

    @Column(name = "user_id")
    private Long userId;

    private boolean isUsed;

    @Embedded
    private UserCouponDateInfo userCouponDateInfo;
}
