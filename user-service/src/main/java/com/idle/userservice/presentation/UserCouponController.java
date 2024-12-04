package com.idle.userservice.presentation;

import com.idle.userservice.application.UserCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/coupons")
public class UserCouponController {
    private final UserCouponService userCouponService;

    @PostMapping("/{coupon-id}/{user-id}")
    public void issuedCoupon(@PathVariable("user-id") Long userId , @PathVariable("coupon-id") Long couponId) {
        userCouponService.issuedCoupon(userId , couponId);
    }

    @GetMapping("/{coupon-id}/{user-id}")
    public boolean hasCoupon(@PathVariable("user-id") Long userId , @PathVariable("coupon-id") Long couponId) {
        return userCouponService.hasCoupon(userId , couponId);
    }
}
