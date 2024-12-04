package com.idle.couponservice.domain;

import java.util.List;

public interface CouponRepository {

    List<Coupon> findByCouponOwnerUserId(Long userId);
}
