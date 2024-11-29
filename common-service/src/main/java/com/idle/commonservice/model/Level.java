package com.idle.commonservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable @Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class Level {
    @Column(name = "level")
    private int value;
    public static Level from(int level) {
        return Level.builder().value(level).build();
    }

    public Level levelUp() {
        System.out.println("JIWON currentLevel = " + this.value);
        return Level.builder()
                .value(this.value+1)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Level level = (Level) o;
        return getValue() == level.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
