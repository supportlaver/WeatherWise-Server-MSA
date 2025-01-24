package com.idle.checkservice.stream.in;

import com.idle.checkservice.event.AggregatedResult;
import com.idle.checkservice.event.CouponIssuedEvent;
import com.idle.checkservice.event.DailyMissionVerifiedResultEvent;
import com.idle.checkservice.event.UserCheckResultEvent;
import com.idle.checkservice.stream.out.CouponIssuedEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Component @Slf4j
@RequiredArgsConstructor
public class CheckResultListener {

    /**
     * Thread-safe ConcurrentHashMap을 사용하여 correlationId 별 AggregatedResult를 관리
     */
    private final Map<String, AggregatedResult> results = new ConcurrentHashMap<>();
    // private final Map<String, AggregatedResult> results = new HashMap<>();
    private final CouponIssuedEventPublisher couponPublisher;

    /**
     * 함수형 Consumer 빈 정의
     */
    @Bean
    public Consumer<UserCheckResultEvent> resultUser() {
        return message -> {
            handleUserCheckResult(message);
        };
    }

    @Bean
    public Consumer<DailyMissionVerifiedResultEvent> resultCreatedMission() {
        return message -> {
            handleDailyMissionVerifiedResult(message);
        };
    }

    /**
     * UserCheckResultEvent 처리 메서드
     */
    private void handleUserCheckResult(UserCheckResultEvent event) {
        String correlationId = event.getCorrelationId();

        // 결과 저장 또는 업데이트
        AggregatedResult result = results.computeIfAbsent(correlationId, id
                -> new AggregatedResult(id, event.getUserId() , event.getCouponId()));
        result.setUserCheckResult(event.isUserCheckResult());

        // 조건 확인
        checkAndPublishCoupon(result, correlationId);
    }

    /**
     * DailyMissionVerifiedResultEvent 처리 메서드
     */
    private void handleDailyMissionVerifiedResult(DailyMissionVerifiedResultEvent event) {
        String correlationId = event.getCorrelationId();

        // 결과 저장 또는 업데이트
        AggregatedResult result = results.computeIfAbsent(correlationId, id
                -> new AggregatedResult(id, event.getUserId() , event.getCouponId()));
        result.setDailyMissionVerifiedResult(event.isDailyMissionVerifiedResult());

        // 조건 확인
        checkAndPublishCoupon(result, correlationId);
    }

    private void checkAndPublishCoupon(AggregatedResult result, String correlationId) {
        if (result.isAllConditionsMet()) {
            // 모든 조건이 충족되면 쿠폰 발행 이벤트 발행
            log.info("[COUPON_ISSUED] 쿠폰 조건 부합 (이벤트 발행)");
            couponPublisher.publishCouponIssuedEvent(new CouponIssuedEvent(result.getUserId(), result.getCouponId()));
            // 상태 삭제
            results.remove(correlationId);
        }

        log.error("[FAIL_COUPON_ISSUED] 쿠폰 조건 부적합 (이벤트 발행 실패)");
    }
}
