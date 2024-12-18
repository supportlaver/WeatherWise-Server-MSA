package com.idle.couponservice.infrastruture;

import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
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
    public Coupon findById(Long couponId) {
        return couponRepository.findById(couponId).orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_COUPON));
    }

    @Override
    public Coupon findByIdForLock(Long couponId) {
        return couponRepository.findByIdWithPessimisticLock(couponId).orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_COUPON));
    }
}
