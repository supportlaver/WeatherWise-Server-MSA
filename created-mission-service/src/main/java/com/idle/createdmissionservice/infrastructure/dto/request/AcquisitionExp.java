package com.idle.createdmissionservice.infrastructure.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class AcquisitionExp {
    private Long userId;
    private int exp;

    public static AcquisitionExp of(Long userId , int exp) {
        return AcquisitionExp.builder()
                .userId(userId)
                .exp(exp)
                .build();
    }
}
