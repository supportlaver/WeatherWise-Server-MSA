package com.idle.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter @Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class Exp {
    private int value;

    public static Exp from(int exp) {
        return Exp.builder().value(exp).build();
    }

    public Exp calculateExp(int totalExp) {
        System.out.println("JIWON totalExp = " + this.value);
        return Exp.builder()
                .value(totalExp - 100)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exp exp = (Exp) o;
        return getValue() == exp.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
