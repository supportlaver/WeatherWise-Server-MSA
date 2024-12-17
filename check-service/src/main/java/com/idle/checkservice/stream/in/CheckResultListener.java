package com.idle.checkservice.stream.in;

import com.idle.checkservice.event.AggregatedResult;
import com.idle.checkservice.event.CouponIssuedEvent;
import com.idle.checkservice.event.DailyMissionVerifiedResultEvent;
import com.idle.checkservice.event.UserCheckResultEvent;
import com.idle.checkservice.stream.out.CouponIssuedEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class CheckResultListener {

    /**
     * Thread-safe ConcurrentHashMap을 사용하여 correlationId 별 AggregatedResult를 관리
     */
    // private final Map<String, AggregatedResult> results = new ConcurrentHashMap<>();
    private final Map<String, AggregatedResult> results = new HashMap<>();
    private final CouponIssuedEventPublisher couponPublisher;

    /**
     * 함수형 Consumer 빈 정의
     */
    @Bean
    public Consumer<UserCheckResultEvent> resultUser() {
        return message -> {
            System.out.println("resultUser-jiwon");
            System.out.println(message.isUserCheckResult());
            System.out.println(message.getCouponId());
            System.out.println(message.getCorrelationId());
            handleUserCheckResult(message);
        };
    }

    @Bean
    public Consumer<DailyMissionVerifiedResultEvent> resultCreatedMission() {
        return message -> {
            System.out.println("resultCreatedMission-jiwon");
            System.out.println(message.isDailyMissionVerifiedResult());
            System.out.println(message.getCouponId());
            System.out.println(message.getCorrelationId());
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
        System.out.println("result = " + result);
        if (result.isAllConditionsMet()) {
            // 모든 조건이 충족되면 쿠폰 발행 이벤트 발행
            System.out.println("조건 부합");
            couponPublisher.publishCouponIssuedEvent(new CouponIssuedEvent(result.getUserId(), result.getCouponId()));

            // 상태 삭제
            results.remove(correlationId);
        }
    }
}
