package com.idle.missionservice.domain;

import com.idle.commonservice.base.BaseEntity;
import com.idle.commonservice.jpa.PointConverter;
import com.idle.commonservice.model.Point;
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

    @Convert(converter = PointConverter.class)
    @Column(name = "reward_point")
    private Point rewardPoint;

    @Enumerated(STRING)
    private WeatherType weatherType;
}
