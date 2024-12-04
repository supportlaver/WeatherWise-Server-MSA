package com.idle.userservice.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Coupon 바운디드 컨텍스트와 통신할 Client
 */
@FeignClient(name = "coupon-service" , url = "http://localhost:8081/api/coupons")
public interface UserServiceClient {

}
