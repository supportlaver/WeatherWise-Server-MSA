package com.idle.couponservice.presentation;

import com.idle.commonservice.annotation.UserId;
import com.idle.commonservice.base.BaseResponse;
import com.idle.couponservice.application.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {
    private final CouponService couponService;

    /**
     * 내가 보유하고 있는 CouponList 확인하기
     */
    @GetMapping
    public void getCouponList(@UserId Long userId) {
        couponService.getCouponList(userId);


    }

    /**
     * Coupon 발급 받기
     */
    @PostMapping("/{coupon-id}")
    public void receiveCoupon(@UserId Long userId , @PathVariable("coupon-id") Long couponId) {
    }
}
