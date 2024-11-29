package com.idle.createdmissionservice.domain;


import java.time.LocalDate;
import java.util.List;

public interface CreatedMissionRepository {
    List<CreatedMission> findCreatedMissionByDate(Long userId , LocalDate date);
}
