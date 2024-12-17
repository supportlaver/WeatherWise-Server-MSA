package com.idle.couponservice.application;

import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.couponservice.domain.Coupon;
import com.idle.couponservice.domain.CouponIssuedService;
import com.idle.couponservice.domain.CouponRepository;
import com.idle.couponservice.infrastruture.event.CouponIssuedEvent;
import com.idle.couponservice.infrastruture.event.UserConditionCheckEvent;
import com.idle.couponservice.infrastruture.stream.out.CouponIssuedEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponIssuedEventPublisher eventPublisher;


    public void getCouponList(Long userId) {

    }


    @Transactional
    public void receiveCoupon(Long userId, Long couponId) {
        // 1. Coupon 의 수량이 남았는가?
        // Coupon coupon = couponRepository.findById(couponId);
        Coupon coupon = couponRepository.findByIdForLock(couponId);
        coupon.issue();

        // 쿠폰 발급 조건 확인을 Event 로 발행
        String correlationId = UUID.randomUUID().toString();
        UserConditionCheckEvent userConditionCheckEvent =
                new UserConditionCheckEvent(correlationId , userId , couponId , LocalDateTime.now());
        eventPublisher.publishToCheckTopic(userConditionCheckEvent);
    }
}
