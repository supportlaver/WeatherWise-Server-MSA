package com.idle.missionservice.domain;

import com.idle.weather.common.model.Point;
import com.idle.weather.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.EnumType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Mission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    private String name;
    private String description;

    @Convert(converter = Point.class)
    @Column(name = "reward_point")
    private Point rewardPoint;

    @Enumerated(STRING)
    private WeatherType weatherType;
}
