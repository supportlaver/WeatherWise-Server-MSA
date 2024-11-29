package com.idle.createdmissionservice.domain;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CreatedMissionRepository {
    List<CreatedMission> findCreatedMissionByDate(Long userId , LocalDate date);

    CreatedMission findById(Long createdMissionId);
}
