package com.idle.couponservice.application;

import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.couponservice.domain.Coupon;
import com.idle.couponservice.domain.CouponRepository;
import com.idle.couponservice.infrastruture.event.CouponIssuedEvent;
import com.idle.couponservice.infrastruture.event.UserConditionCheckEvent;
import com.idle.couponservice.infrastruture.stream.in.CheckResultListener;
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
    private final CheckResultListener checkResultListener;

    public void getCouponList(Long userId) {

    }

    /**
     * 동시성 생각
     * Aggregator 를 사용
     */
    @Transactional
    public void receiveCoupon(Long userId, Long couponId) {
        // 1. Coupon 의 수량이 남았는가?
        Coupon coupon = couponRepository.findById(couponId);
        if (!coupon.checkQuantity()) {
            throw new BaseException(ErrorCode.EXCEEDED_QUANTITY);
        }

        // 쿠폰 발급 조건 확인을 Event 로 발행
        String correlationId = UUID.randomUUID().toString();
        UserConditionCheckEvent userConditionCheckEvent =
                new UserConditionCheckEvent(correlationId , userId , couponId , LocalDateTime.now());
        eventPublisher.publishToCheckTopic(userConditionCheckEvent);
    }

    /**
     * 이거는 동시성을 생각하지 않은 경우
     */
    public void receiveCouponV0(Long userId, Long couponId) {
        // 1. Coupon 의 수량이 남았는가?
        Coupon coupon = couponRepository.findById(couponId);
        if (!coupon.checkQuantity()) {
            throw new BaseException(ErrorCode.EXCEEDED_QUANTITY);
        }

        // 쿠폰 발급 조건 확인을 Event 로 발행
        String correlationId = UUID.randomUUID().toString();
        UserConditionCheckEvent userConditionCheckEvent =
                new UserConditionCheckEvent(correlationId , userId , couponId , LocalDateTime.now());

        eventPublisher.publishToCheckTopic(userConditionCheckEvent);


        // 5. Result 토픽 메시지 확인
        if (!checkResultListener.isCouponProcessingSuccessful(correlationId)) {
            // 어떤 곳에서 false 가 나왔는지에 따라 ErrorCode 확실하게 만들기
            throw new BaseException(ErrorCode.COUPON_PROCESSING_FAILED);
        }

        // 쿠폰 발급을 Event 로 발행
        CouponIssuedEvent couponIssuedEvent = new CouponIssuedEvent(userId, couponId);
        eventPublisher.publishToIssuedTopic(couponIssuedEvent);
    }
}
