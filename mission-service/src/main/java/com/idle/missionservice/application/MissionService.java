package com.idle.missionservice.application;

import com.idle.missionservice.application.dto.response.MissionResponse;
import com.idle.missionservice.domain.Mission;
import com.idle.missionservice.domain.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;

    public List<MissionResponse> getMissionsInfo(List<Long> missionIds) {
        // Repository 에서 미션 가져오기
        List<Mission> missions = missionIds.stream().map(missionRepository::findById).toList();

        return missions.stream().map(MissionResponse::from).collect(toList());
    }
}
