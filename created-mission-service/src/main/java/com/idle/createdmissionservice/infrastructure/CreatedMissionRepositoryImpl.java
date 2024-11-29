package com.idle.createdmissionservice.infrastructure;

import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.createdmissionservice.domain.CreatedMission;
import com.idle.createdmissionservice.domain.CreatedMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CreatedMissionRepositoryImpl implements CreatedMissionRepository {

    private final CreatedMissionJpaRepository createdMissionRepository;

    @Override
    public List<CreatedMission> findCreatedMissionByDate(Long userId, LocalDate date) {
        return createdMissionRepository.findMissionHistoryByDate(userId, date);
    }

    @Override
    public CreatedMission findById(Long createdMissionId) {
        return createdMissionRepository.findById(createdMissionId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_CREATED_MISSION));
    }
}
