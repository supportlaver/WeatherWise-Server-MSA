package com.idle.couponservice.infrastruture;

import com.idle.couponservice.domain.user.User;
import com.idle.couponservice.domain.user.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponJpaRepository extends JpaRepository<UserCoupon, Long> {

}
