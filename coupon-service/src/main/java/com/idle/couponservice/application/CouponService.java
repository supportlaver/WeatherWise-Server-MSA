package com.idle.couponservice.application;

import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.couponservice.domain.Coupon;
import com.idle.couponservice.domain.CouponRepository;
import com.idle.couponservice.infrastruture.CreatedMissionServiceClient;
import com.idle.couponservice.infrastruture.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;
    private final CreatedMissionServiceClient createdMissionClient;
    private final UserServiceClient userClient;

    public void getCouponList(Long userId) {

    }

    /**
     * 여기서 동시성 제어 (쿠폰 수량에 맞게)
     */
    @Transactional
    public void receiveCoupon(Long userId, Long couponId) {
        // 1. Coupon 의 수량이 남았는가?
        Coupon coupon = couponRepository.findById(couponId);
        if (!coupon.checkQuantity()) {
            throw new BaseException(ErrorCode.EXCEEDED_QUANTITY);
        }

        // 2. User 가 미션을 하나 이상 수행했는가?
        boolean hasUserCompletedAnyMission = createdMissionClient.hasUserCompletedAnyMission(userId , LocalDate.now());

        if (!hasUserCompletedAnyMission) {
            throw new BaseException(ErrorCode.NOT_COMPLETED_ANY_MISSION);
        }

        // 3. User 가 이미 같은 쿠폰을 발급 받았는가?
        boolean hasSameCoupon = userClient.hasSameCoupon(userId, couponId);

        if (hasSameCoupon) {
            throw new BaseException(ErrorCode.ALREADY_ISSUED_COUPON);
        }


        // 3. 1,2번이 모두 통과된다면 Coupon 을 발급
        userClient.issuedCoupon(userId , couponId);
    }
}
