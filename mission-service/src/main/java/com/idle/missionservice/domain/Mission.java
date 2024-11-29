package com.idle.missionservice.domain;

import com.idle.commonservice.base.BaseEntity;
import com.idle.commonservice.jpa.ExpConverter;
import com.idle.commonservice.model.Exp;
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

    @Embedded
    private MissionInfo missionInfo;

    @Convert(converter = ExpConverter.class)
    @Column(name = "reward_point")
    private Exp rewardExp;

    @Enumerated(STRING)
    private WeatherType weatherType;
}
