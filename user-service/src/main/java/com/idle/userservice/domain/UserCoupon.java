package com.idle.userservice.domain;

import com.idle.commonservice.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
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

    public static UserCoupon issuedCoupon(Long couponId , Long userId) {
        return UserCoupon.builder()
                .couponId(CouponId.builder().couponId(couponId).build())
                .userId(userId)
                .isUsed(false)
                .userCouponDateInfo(UserCouponDateInfo.issuedCoupon())
                .build();
    }
}
