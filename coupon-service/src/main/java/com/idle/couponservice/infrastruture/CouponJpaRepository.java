package com.idle.couponservice.infrastruture;

import com.idle.couponservice.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponJpaRepository extends JpaRepository<Coupon , Long> {
    @Query("SELECT c FROM Coupon c WHERE c.couponOwner.userId = :userId")
    List<Coupon> findByCouponOwnerUserId(@Param("userId") Long userId);
}
