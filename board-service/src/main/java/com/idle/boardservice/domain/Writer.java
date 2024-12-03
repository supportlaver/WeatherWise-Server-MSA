package com.idle.boardservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable @AllArgsConstructor
@Builder
@Getter @NoArgsConstructor
public class Writer {
    @Column(name = "user_id")
    private Long userId;

    public static Writer of(Long userId) {
        return Writer.builder().userId(userId).build();
    }
}
