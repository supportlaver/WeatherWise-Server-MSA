package com.idle.couponservice.infrastruture.stream.in;

import com.idle.couponservice.infrastruture.event.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Component
public class CheckResultListener {

    /**
     * Thread-safe ConcurrentHashMap을 사용하여 correlationId 별 AggregatedResult를 관리
     */
    private final Map<String, AggregatedResult> aggregatedResults = new ConcurrentHashMap<>();

    /**
     * 함수형 Consumer 빈 정의
     */
    @Bean
    public Consumer<UserCheckResultEvent> resultUser() {
        return message -> {
            System.out.println("ResultUser-jiwon");
            System.out.println(message.isUserCheckResult());
            System.out.println(message.getCouponId());
            System.out.println(message.getCorrelationId());
            handleUserCheckResult(message);
        };
    }

    @Bean
    public Consumer<DailyMissionVerifiedResultEvent> resultCreatedMission() {
        return message -> {
            try {
                System.out.println("Processing message: " + message);
                handleDailyMissionVerifiedResult(message); // 실제 메시지 처리 로직
            } catch (Exception e) {
                System.err.println("Error processing message: " + e.getMessage());
                // 예외를 처리하거나 적절한 로깅/대응
            }
        };
    }


    /**
     * UserCheckResultEvent 처리 메서드
     */
    private void handleUserCheckResult(UserCheckResultEvent event) {

        String correlationId = event.getCorrelationId();
        Long userId = event.getUserId();

        // AggregatedResult 가져오기 또는 생성
        AggregatedResult aggregatedResult = aggregatedResults
                .computeIfAbsent(correlationId, id -> new AggregatedResult(userId));

        // 조건 결과 업데이트
        aggregatedResult.setUserCheckResult(event.isUserCheckResult());

        // 모든 조건이 충족되었는지 확인
        if (aggregatedResult.isAllConditionsMet()) {
            aggregatedResults.remove(correlationId); // 상태 제거
        }
        System.out.println("OK-USER");

        for (String s : aggregatedResults.keySet()) {
            AggregatedResult res = aggregatedResults.get(s);
            System.out.println("res = " + res);
        }
    }

    /**
     * DailyMissionVerifiedResultEvent 처리 메서드
     */
    private void handleDailyMissionVerifiedResult(DailyMissionVerifiedResultEvent event) {
        String correlationId = event.getCorrelationId();
        Long userId = event.getUserId();

        // AggregatedResult 가져오기 또는 생성
        AggregatedResult aggregatedResult = aggregatedResults.computeIfAbsent(correlationId, id -> new AggregatedResult(userId));

        // 조건 결과 업데이트
        aggregatedResult.setDailyMissionVerifiedResult(event.isDailyMissionVerifiedResult());

        // 모든 조건이 충족되었는지 확인
        if (aggregatedResult.isAllConditionsMet()) {
            aggregatedResults.remove(correlationId); // 상태 제거
        }
        System.out.println("OK-CREATED-MISSION");

        for (String s : aggregatedResults.keySet()) {
            AggregatedResult res = aggregatedResults.get(s);
            System.out.println("res = " + res);
        }
    }

    /**
     * 쿠폰 발급 성공 여부 확인 메서드
     */
    public boolean isCouponProcessingSuccessful(String correlationId) {
        AggregatedResult result = aggregatedResults.get(correlationId);
        System.out.println("result = " + result);
        System.out.println("CheckResultListener.isCouponProcessingSuccessful");
        return result != null && result.isAllConditionsMet();
    }
}
