package com.idle.checkservice.stream.out;


import com.idle.checkservice.event.CouponIssuedEvent;
import com.idle.checkservice.event.UserConditionCheckEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
@Slf4j
public class CouponIssuedEventPublisher {
    // 비동기적으로 이벤트를 발행
    private final Sinks.Many<CouponIssuedEvent> issuedSink = Sinks.many().unicast().onBackpressureBuffer();


    // Issued Sink
    @Bean
    public Supplier<Flux<CouponIssuedEvent>> issued() {
        return () -> issuedSink.asFlux()
                .doOnError(error -> {
                    // 에러 처리 로직
                    log.error("Error in couponEventSupplier {} " , error.getMessage());
                });
    }

    public void publishCouponIssuedEvent(CouponIssuedEvent event) {
        System.out.println("쿠폰 지급이벤트 발행");
        issuedSink.tryEmitNext(event);
    }
}
