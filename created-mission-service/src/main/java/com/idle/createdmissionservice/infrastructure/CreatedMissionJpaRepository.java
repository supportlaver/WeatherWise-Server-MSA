package com.idle.createdmissionservice.infrastructure;

import com.idle.createdmissionservice.domain.CreatedMission;
import com.idle.createdmissionservice.presentation.CreatedMissionController;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CreatedMissionJpaRepository extends JpaRepository<CreatedMission , Long> {

    @Query("SELECT MH FROM CreatedMission AS MH WHERE MH.challenger.userId = :userId " +
            "AND MH.createdAt BETWEEN :#{#date.atStartOfDay()} AND :#{#date.plusDays(1).atStartOfDay()}")
    List<CreatedMission> findMissionHistoryByDate(
            @Param("userId") Long userId,
            @Param("date") LocalDate date);
}
