package com.idle.couponservice.domain;

import java.util.List;

public interface CouponRepository {
    Coupon findById(Long couponId);
}
