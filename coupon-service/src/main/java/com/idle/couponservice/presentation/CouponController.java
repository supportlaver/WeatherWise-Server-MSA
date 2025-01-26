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


    @PostMapping("/{user-id}/{coupon-id}")
    public void receiveCoupon(@PathVariable("user-id") Long userId , @PathVariable("coupon-id") Long couponId) {
        log.info("[RECEIVE_COUPON] : Request UserId = {} " , userId);
        couponService.receiveCoupon(userId , couponId);
    }
}
