package com.idle.couponservice.infrastruture;

import com.idle.commonservice.annotation.UserId;
import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * User 바운디드 컨텍스트와 통신할 Client
 */
@FeignClient(name = "user-service" , url = "http://localhost:8081/api/users")
public interface UserServiceClient {

    @PostMapping("/coupons/{coupon-id}/{user-id}")
    void issuedCoupon(@PathVariable("user-id") Long userId , @PathVariable("coupon-id") Long couponId);

    @GetMapping("/coupons/{coupon-id}/{user-id}")
    boolean hasSameCoupon(@PathVariable("user-id") Long userId , @PathVariable("coupon-id") Long couponId);
}
