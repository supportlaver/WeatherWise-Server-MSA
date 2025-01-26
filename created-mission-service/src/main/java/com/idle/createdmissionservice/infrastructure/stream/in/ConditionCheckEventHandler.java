package com.idle.createdmissionservice.infrastructure.stream.in;


import com.idle.createdmissionservice.infrastructure.CreatedMissionJpaRepository;
import com.idle.createdmissionservice.infrastructure.event.DailyMissionVerifiedResultEvent;
import com.idle.createdmissionservice.infrastructure.event.UserConditionCheckEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration @Slf4j
@RequiredArgsConstructor
public class ConditionCheckEventHandler {

    private final StreamBridge streamBridge;
    private final CreatedMissionJpaRepository createdMissionJpaRepository;

    @Bean
    public Consumer<UserConditionCheckEvent> check() {
        return message -> {
            boolean result = createdMissionCheck(message);
            streamBridge.send("created-mission-result", new DailyMissionVerifiedResultEvent(message.getUserId() , message.getCouponId(),
                    message.getCorrelationId() , result));
        };
    }

    private boolean createdMissionCheck(UserConditionCheckEvent event) {
        return createdMissionJpaRepository.hasCompletedMissionToday(event.getUserId() , event.getLocalDateTime());
    }
}
