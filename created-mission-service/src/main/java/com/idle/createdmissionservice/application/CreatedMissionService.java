package com.idle.createdmissionservice.application;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.idle.createdmissionservice.application.dto.request.CreateMission;
import com.idle.createdmissionservice.application.dto.response.CreatedMissionsView;
import com.idle.createdmissionservice.application.dto.response.FileUpload;
import com.idle.createdmissionservice.application.dto.response.MissionAuthenticateView;
import com.idle.createdmissionservice.application.dto.response.UserData;
import com.idle.createdmissionservice.domain.*;
import com.idle.createdmissionservice.infrastructure.MissionServiceClient;
import com.idle.createdmissionservice.infrastructure.UserServiceClient;
import com.idle.createdmissionservice.infrastructure.dto.request.AcquisitionExp;
import com.idle.createdmissionservice.infrastructure.dto.response.MissionResponse;
import com.idle.createdmissionservice.infrastructure.dto.response.UserAcquisitionExpResponse;
import com.idle.createdmissionservice.infrastructure.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service @Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreatedMissionService {

    private final CreatedMissionRepository createdMissionRepository;
    private final UserServiceClient userClient;
    private final MissionServiceClient missionClient;
    private final AIMissionProvider createMissionProvider;
    private final MissionAuthenticationService missionAuthenticationService;



    @Transactional
    public MissionAuthenticateView authMission(Long userId, Long createdMissionId, MultipartFile imageFile) {

        CreatedMission createdMission = createdMissionRepository.findById(createdMissionId);
        Long missionId = createdMission.getBasedMission().getMissionId();
        MissionResponse mission = missionClient.findById(missionId);

        boolean authResult = missionAuthenticationService.authenticateMission(createdMission, mission, imageFile);

        if (!authResult) {
            createdMission.failAuthentication();
            return MissionAuthenticateView.fail();
        }

        createdMission.successAuthentication();

        UserAcquisitionExpResponse res = userClient.acquisitionExp(AcquisitionExp.of(userId, mission.getExp()));

        return MissionAuthenticateView.success(true, mission.getExp(), res.getUserLevel(), res.getExp());
    }


    public List<CreatedMissionsView> getMissionList(LocalDate date , Long userId) {
        UserResponse res = userClient.findById(userId);

        UserData user = UserData.of(res.getId(), res.getNickname());

        List<CreatedMission> createdMissions = createdMissionRepository.findCreatedMissionByDate(user.getId(), date);
        List<Long> missionIds = createdMissions.stream().map(cm -> cm.getBasedMission().getMissionId()).toList();

        List<MissionResponse> missionsRes = missionClient.getMissionsInfo(missionIds);


        Map<Long, MissionResponse> missionMap = missionsRes.stream()
                .collect(Collectors.toMap(MissionResponse::getMissionId, Function.identity()));

        return createdMissions.stream()
                .map(cm -> {
                    MissionResponse mission = missionMap.get(cm.getBasedMission().getMissionId());
                    return CreatedMissionsView.of(cm, mission , user);
                })
                .toList();
    }


    public CreatedMissionsView getMission(Long createdMissionId) {
        CreatedMission createdMission = createdMissionRepository.findById(createdMissionId);

        MissionResponse mission = missionClient.findById(createdMission.getBasedMission().getMissionId());

        UserResponse res = userClient.findById(createdMission.getChallenger().getUserId());

        UserData user = UserData.of(res.getId(), res.getNickname());

        return CreatedMissionsView.of(createdMission , mission , user);
    }

    public boolean getCompletedAnyMission(Long userId, LocalDate date) {
        List<CreatedMission> todayCreatedMission = createdMissionRepository.findCreatedMissionByDate(userId, date);
        return todayCreatedMission.stream().anyMatch(CreatedMission::isCompleted);
    }
}
