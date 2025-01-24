package com.idle.createdmissionservice.domain;

import com.idle.createdmissionservice.application.FileStorageService;
import com.idle.createdmissionservice.application.dto.response.FileUpload;
import com.idle.createdmissionservice.infrastructure.dto.response.MissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 도메인 서비스
 */
@Service
@RequiredArgsConstructor
public class MissionAuthenticationService {

    private final AIMissionProvider aiMissionProvider;
    private final FileStorageService fileStorageService;

    public boolean authenticateMission(CreatedMission createdMission, MissionResponse mission, MultipartFile imageFile) {
        FileUpload fileUploadInfo = fileStorageService.uploadFile(imageFile);
        createdMission.updateImageFileInfo(fileUploadInfo.getOriginalFileName(), fileUploadInfo.getUploadFileUrl());
        return aiMissionProvider.authMission(
                MissionAuth.of(mission.getName(), mission.getQuestion(), fileUploadInfo.getUploadFileUrl())
        );
    }
}
