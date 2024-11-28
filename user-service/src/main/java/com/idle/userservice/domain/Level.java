package com.idle.userservice.domain;

import com.idle.commonservice.model.Point;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable @Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class Level {
    @Column(name = "level")
    private int value;
    public static Level from(int point) {
        return Level.builder().value(point).build();
    }
}
