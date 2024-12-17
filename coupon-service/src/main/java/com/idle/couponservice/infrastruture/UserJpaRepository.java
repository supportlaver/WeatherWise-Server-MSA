package com.idle.couponservice.infrastruture;

import com.idle.couponservice.domain.Coupon;
import com.idle.couponservice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

}
