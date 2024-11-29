package com.idle.createdmissionservice.infrastructure;

import com.idle.createdmissionservice.domain.CreatedMission;
import com.idle.createdmissionservice.domain.CreatedMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CreatedMissionRepositoryImpl implements CreatedMissionRepository {

    private final CreatedMissionJpaRepository createdMissionRepository;

    @Override
    public List<CreatedMission> findCreatedMissionByDate(Long userId, LocalDate date) {
        return createdMissionRepository.findMissionHistoryByDate(userId, date);
    }
}
