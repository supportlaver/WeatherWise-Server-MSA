package com.idle.userservice.domain;

import com.idle.weather.common.jpa.PointConverter;
import com.idle.weather.common.model.Point;
import com.idle.weather.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@ToString
@Entity
@Getter
@DynamicUpdate
@Table(name = "users") @Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Convert(converter = PointConverter.class)
    private Point currentPoint;

    @Embedded
    private Level level;

    @Embedded
    private Password password;

    @Embedded
    private LoginId loginId;

    @Embedded
    private PersonalWeatherTraits personalWeatherTraits;
}
