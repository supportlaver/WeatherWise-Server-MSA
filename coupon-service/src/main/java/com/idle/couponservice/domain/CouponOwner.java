package com.idle.couponservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class CouponOwner {

    @Column(name = "user_id")
    private Long userId;
}
