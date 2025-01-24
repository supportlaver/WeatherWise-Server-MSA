package com.idle.userservice.infrastructure.stream.in;

import com.idle.commonservice.event.Event;
import com.idle.userservice.infrastructure.UserJpaRepository;
import com.idle.userservice.infrastructure.event.CouponIssuedEvent;
import com.idle.userservice.infrastructure.event.UserCheckResultEvent;
import com.idle.userservice.infrastructure.event.UserConditionCheckEvent;
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
    private final UserJpaRepository userJpaRepository;
    private final CouponIssuedService couponIssuedService;

    @Bean
    public Consumer<UserConditionCheckEvent> check() {
        return message -> {
            boolean result = userCheck(message);
            streamBridge.send("user-result", new UserCheckResultEvent(message.getUserId(),message.getCouponId(),
                    message.getCorrelationId(), result));
        };
    }


    @Bean
    public Consumer<CouponIssuedEvent> issued() {
        return message -> {
            couponIssuedService.issuedCoupon(message.getUserId(), message.getCouponId());
        };
    }

    private boolean userCheck(UserConditionCheckEvent event) {
        return userJpaRepository.existsCouponByUserIdAndCouponId(event.getUserId() , event.getCouponId());
    }
}
