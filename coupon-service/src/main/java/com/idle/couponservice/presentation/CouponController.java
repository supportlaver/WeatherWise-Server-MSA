package com.idle.couponservice.presentation;

import com.idle.commonservice.annotation.UserId;
import com.idle.commonservice.base.BaseResponse;
import com.idle.couponservice.application.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @Slf4j
@RequiredArgsConstructor
@RequestMapping("/test/api/coupons")
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
     * V0 : Coupon 에서 모두 처리
     * V1 : Aggregator 를 사용하여 처리
     */
    /*@PostMapping("/{coupon-id}")
    public void receiveCoupon(@UserId Long userId , @PathVariable("coupon-id") Long couponId) {
        couponService.receiveCoupon(userId , couponId);
    }*/

    @PostMapping("/{user-id}/{coupon-id}")
    public void receiveCoupon(@PathVariable("user-id") Long userId , @PathVariable("coupon-id") Long couponId) {
        log.info("[RECEIVE_COUPON] : Request UserId = {} " , userId);
        couponService.receiveCoupon(userId , couponId);
    }
}
