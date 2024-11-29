package com.idle.createdmissionservice.application;

import com.idle.createdmissionservice.application.dto.response.CreatedMissionsView;
import com.idle.createdmissionservice.application.dto.response.UserData;
import com.idle.createdmissionservice.domain.CreatedMission;
import com.idle.createdmissionservice.domain.CreatedMissionRepository;
import com.idle.createdmissionservice.infrastructure.MissionServiceClient;
import com.idle.createdmissionservice.infrastructure.UserServiceClient;
import com.idle.createdmissionservice.infrastructure.dto.response.MissionResponse;
import com.idle.createdmissionservice.infrastructure.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreatedMissionService {

    private final CreatedMissionRepository createdMissionRepository;
    private final UserServiceClient userClient;
    private final MissionServiceClient missionClient;


    public List<CreatedMissionsView> getMissionList(LocalDate date , Long userId) {
        // user 정보 가져오기
        UserResponse res = userClient.findById(userId);

        // application DTO 로 변환
        UserData user = UserData.of(res.getId(), res.getNickname());

        // Repository 에서 DATA 가져오기
        List<CreatedMission> createdMissions = createdMissionRepository.findCreatedMissionByDate(user.getId(), date);
        List<Long> missionIds = createdMissions.stream().map(cm -> cm.getBasedMission().getMissionId()).toList();

        // mission 정보 가져오기
        List<MissionResponse> missionsRes = missionClient.getMissionsInfo(missionIds);


        // key : MissionId , Value : MissionRes
        Map<Long, MissionResponse> missionMap = missionsRes.stream()
                .collect(Collectors.toMap(MissionResponse::getMissionId, Function.identity()));

        // CreatedMission 과 Mission 의 정보를 합쳐서 반환
        return createdMissions.stream()
                .map(cm -> {
                    MissionResponse mission = missionMap.get(cm.getBasedMission().getMissionId());
                    return CreatedMissionsView.of(cm, mission , user);
                })
                .toList();
    }
}
