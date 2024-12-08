package com.idle.createdmissionservice.infrastructure;

import com.idle.createdmissionservice.domain.CreatedMission;
import com.idle.createdmissionservice.presentation.CreatedMissionController;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CreatedMissionJpaRepository extends JpaRepository<CreatedMission , Long> {

    @Query("SELECT MH FROM CreatedMission AS MH WHERE MH.challenger.userId = :userId " +
            "AND MH.createdAt >= :#{#date.atStartOfDay()} AND MH.createdAt < :#{#date.plusDays(1).atStartOfDay()}")
    List<CreatedMission> findMissionHistoryByDate(
            @Param("userId") Long userId,
            @Param("date") LocalDate date);

    @Query("SELECT CASE WHEN COUNT(cm) > 0 THEN TRUE ELSE FALSE END " +
            "FROM CreatedMission AS cm " +
            "WHERE cm.challenger.userId = :userId " +
            "AND cm.isCompleted = TRUE " +
            "AND cm.createdAt >= :#{#date.toLocalDate().atStartOfDay()} " +
            "AND cm.createdAt < :#{#date.toLocalDate().plusDays(1).atStartOfDay()}")
    boolean hasCompletedMissionToday(@Param("userId") Long userId, @Param("date") LocalDateTime date);
}
