package com.idle.couponservice.application;

import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.couponservice.domain.Coupon;
import com.idle.couponservice.domain.CouponIssuedService;
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
     * 비즈니스 로직이 애플리케이션 계층에 너무 집중되어 있는 느낌?
     */
    @Transactional
    public void receiveCouponV1(Long userId, Long couponId) {
        // 1. User 가 미션을 하나 이상 수행했는가?
        boolean hasUserCompletedAnyMission = createdMissionClient.hasUserCompletedAnyMission(userId , LocalDate.now());

        if (!hasUserCompletedAnyMission) {
            throw new BaseException(ErrorCode.NOT_COMPLETED_ANY_MISSION);
        }

        // 2. User 가 이미 같은 쿠폰을 발급 받았는가?
        boolean hasSameCoupon = userClient.hasSameCoupon(userId, couponId);

        if (hasSameCoupon) {
            throw new BaseException(ErrorCode.ALREADY_ISSUED_COUPON);
        }

        // 3. 쿠폰 수량 감소
        Coupon coupon = couponRepository.findById(couponId);
        coupon.issue();

        // 4. 1,2,3번이 모두 통과된다면 Coupon 을 발급
        userClient.issuedCoupon(userId , couponId);
    }

    /**
     * 도메인 서비스를 통해 비즈니스 로직을 캡슐화?
     */
    @Transactional
    public void receiveCouponV2(Long userId, Long couponId) {
        CouponIssuedService couponIssuedService = new CouponIssuedService(couponRepository,createdMissionClient,userClient);
        couponIssuedService.issueCoupon(userId,couponId);
    }
}
