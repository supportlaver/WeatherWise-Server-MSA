package com.idle.commonservice.jpa;


import com.idle.commonservice.model.Level;
import com.idle.commonservice.model.Point;
import jakarta.persistence.AttributeConverter;

public class LevelConverter implements AttributeConverter<Level, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Level level) {
        return level == null ? null : level.getValue();
    }

    @Override
    public Level convertToEntityAttribute(Integer value) {
        return value == null ? null : Level.builder().value(value).build();
    }
}
