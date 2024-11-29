package com.idle.missionservice.infrastructure;

import com.idle.missionservice.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionJpaRepository extends JpaRepository<Mission , Long> {
}
