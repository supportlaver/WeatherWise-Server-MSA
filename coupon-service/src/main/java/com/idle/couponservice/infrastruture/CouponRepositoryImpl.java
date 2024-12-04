package com.idle.couponservice.infrastruture;

import com.idle.couponservice.domain.Coupon;
import com.idle.couponservice.domain.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {
    private final CouponJpaRepository couponRepository;

    @Override
    public List<Coupon> findByCouponOwnerUserId(Long userId) {
        return couponRepository.findByCouponOwnerUserId(userId);
    }
}
