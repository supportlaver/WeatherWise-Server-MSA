package com.example.createdmissionservice.domain;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

/**
 * <<Aggregate Root>>
 **/

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CreatedMission {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "created_mission_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Enumerated(STRING)
    private MissionTime missionTime;

    @Embedded
    private Challenger challenger;

    private boolean isCompleted;
}
