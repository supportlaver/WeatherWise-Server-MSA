package com.idle.couponservice.infrastruture.stream.out;


import com.idle.couponservice.infrastruture.event.CouponIssuedEvent;
import com.idle.couponservice.infrastruture.event.UserConditionCheckEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.kafka.support.KafkaIntegrationHeaders;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.converter.KafkaMessageHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
@Slf4j
public class CouponIssuedEventPublisher {

    // 비동기적으로 이벤트를 발행
    private final Sinks.Many<UserConditionCheckEvent> checkSink = Sinks.many().unicast().onBackpressureBuffer();
    private final Sinks.Many<CouponIssuedEvent> issuedSink = Sinks.many().unicast().onBackpressureBuffer();
    private final Sinks.Many<CouponIssuedEvent> issuedSinkV0 = Sinks.many().unicast().onBackpressureBuffer();

    // Condition Check Sink
    @Bean
    public Supplier<Flux<UserConditionCheckEvent>> check() {
        return () -> checkSink.asFlux()
                .doOnError(error -> {
                    // 에러 처리 로직
                    System.err.println("Error in couponEventSupplier: " + error.getMessage());
                });
    }

    // Issued Sink
    @Bean
    public Supplier<Flux<CouponIssuedEvent>> issued() {
        return () -> issuedSink.asFlux()
                .doOnError(error -> {
                    // 에러 처리 로직
                    System.err.println("Error in couponEventSupplier: " + error.getMessage());
                });
    }

    public void publishToCheckTopic(UserConditionCheckEvent event) {
        log.info("publishToCheckTopic");
        checkSink.tryEmitNext(event);
    }

    public void publishToIssuedTopic(CouponIssuedEvent event) {
        issuedSinkV0.tryEmitNext(event);
    }
}
