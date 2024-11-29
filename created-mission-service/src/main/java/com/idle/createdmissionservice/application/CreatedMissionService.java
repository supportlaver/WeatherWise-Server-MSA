package com.idle.createdmissionservice.application;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.idle.createdmissionservice.application.dto.request.CreateMission;
import com.idle.createdmissionservice.application.dto.response.CreatedMissionsView;
import com.idle.createdmissionservice.application.dto.response.FileUpload;
import com.idle.createdmissionservice.application.dto.response.MissionAuthenticateView;
import com.idle.createdmissionservice.application.dto.response.UserData;
import com.idle.createdmissionservice.domain.AIMissionProvider;
import com.idle.createdmissionservice.domain.CreatedMission;
import com.idle.createdmissionservice.domain.CreatedMissionRepository;
import com.idle.createdmissionservice.domain.MissionAuth;
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
    private final AIMissionProvider aiMissionProvider;
    private final FileStorageService fileStorageService;

    public void createMission(Long userId , CreateMission req) {
        // TODO: 11/29/24 미션을 어떻게 만들어줄것인가?
        createMissionProvider.createMission(req.getLatitude() , req.getLongitude());
    }

    @Transactional
    public MissionAuthenticateView authMission(Long userId, Long createdMissionId, MultipartFile imageFile) {
        // createdMission 정보 가져오기
        CreatedMission createdMission = createdMissionRepository.findById(createdMissionId);

        // FileStorage 에 저장
        FileUpload fileUploadInfo = fileStorageService.uploadFile(imageFile);
        createdMission.updateImageFileInfo(fileUploadInfo.getOriginalFileName() , fileUploadInfo.getUploadFileUrl());

        // BasedMission 정보 가져오기
        Long missionId = createdMission.getBasedMission().getMissionId();
        MissionResponse mission = missionClient.findById(missionId);


        // ai 서버에 미션 인증 요청
        boolean authenticationResult = aiMissionProvider
                .authMission(MissionAuth.of(mission.getName(), mission.getQuestion(), fileUploadInfo.getUploadFileUrl()));

        // 인증 실패시
        if (!authenticationResult) {
            // 실패시 ImageUrl 에 Null 로 지정
            createdMission.updateImageFileInfo(null , null);
            return MissionAuthenticateView.fail();
        }

        // 인증 성공시
        // User 경험치 추가 (경험치 추가 할 때 레벨 계산)
        UserAcquisitionExpResponse res = userClient.acquisitionExp(AcquisitionExp.of(userId, mission.getExp()));

        // CreatedMission 상태 변경 (미션 성공)
        createdMission.completedMission();

        return MissionAuthenticateView.success(true,mission.getExp() , res.getUserLevel() , res.getExp());
    }


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


    public CreatedMissionsView getMission(Long createdMissionId) {
        CreatedMission createdMission = createdMissionRepository.findById(createdMissionId);

        MissionResponse mission = missionClient.findById(createdMission.getBasedMission().getMissionId());

        // user 정보 가져오기
        UserResponse res = userClient.findById(createdMission.getChallenger().getUserId());

        // application DTO 로 변환
        UserData user = UserData.of(res.getId(), res.getNickname());

        return CreatedMissionsView.of(createdMission , mission , user);
    }
}
