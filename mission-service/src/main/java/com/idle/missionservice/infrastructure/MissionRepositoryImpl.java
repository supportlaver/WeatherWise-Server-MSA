package com.idle.missionservice.infrastructure;

import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.missionservice.domain.Mission;
import com.idle.missionservice.domain.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MissionRepositoryImpl implements MissionRepository {

    private final MissionJpaRepository missionRepository;

    @Override
    public Mission findById(Long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_MISSION));
    }
}
