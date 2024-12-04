package com.idle.couponservice.application;

import com.idle.couponservice.domain.Coupon;
import com.idle.couponservice.domain.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;

    public void getCouponList(Long userId) {
        List<Coupon> couponList = couponRepository.findByCouponOwnerUserId(userId);
    }
}
