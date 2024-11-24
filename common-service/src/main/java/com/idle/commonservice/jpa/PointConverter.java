package com.idle.commonservice.jpa;


import com.idle.commonservice.model.Point;
import jakarta.persistence.AttributeConverter;

public class PointConverter implements AttributeConverter<Point, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Point point) {
        return point == null ? null : point.getValue();
    }

    @Override
    public Point convertToEntityAttribute(Integer value) {
        return value == null ? null : Point.builder().value(value).build();
    }
}
