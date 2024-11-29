package com.idle.commonservice.jpa;


import com.idle.commonservice.model.Exp;
import jakarta.persistence.AttributeConverter;

public class ExpConverter implements AttributeConverter<Exp, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Exp exp) {
        return exp == null ? null : exp.getValue();
    }

    @Override
    public Exp convertToEntityAttribute(Integer value) {
        return value == null ? null : Exp.builder().value(value).build();
    }
}
