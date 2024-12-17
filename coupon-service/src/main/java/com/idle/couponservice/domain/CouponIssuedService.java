package com.idle.couponservice.domain;

import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.couponservice.infrastruture.CreatedMissionServiceClient;
import com.idle.couponservice.infrastruture.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * domain 서비스
 */

public class CouponIssuedService {

    private final CouponRepository couponRepository;

    // HTTP 통신 Client
    private final CreatedMissionServiceClient createdMissionClient;
    private final UserServiceClient userClient;

    public CouponIssuedService(
            CouponRepository couponRepository,
            CreatedMissionServiceClient createdMissionClient,
            UserServiceClient userClient
    ) {
        this.couponRepository = couponRepository;
        this.createdMissionClient = createdMissionClient;
        this.userClient = userClient;
    }

    public void issueCoupon(Long userId, Long couponId) {
        // 1. 사용자의 미션 수행 여부 확인
        if (!createdMissionClient.hasUserCompletedAnyMission(userId, LocalDate.now())) {
            throw new BaseException(ErrorCode.NOT_COMPLETED_ANY_MISSION);
        }

        // 2. 사용자의 쿠폰 발급 여부 확인
        if (userClient.hasSameCoupon(userId, couponId)) {
            throw new BaseException(ErrorCode.ALREADY_ISSUED_COUPON);
        }

        Coupon coupon = couponRepository.findById(couponId);

        // 3. 쿠폰 수량 감소
        coupon.issue();

        userClient.issuedCoupon(userId,couponId);
    }


}
