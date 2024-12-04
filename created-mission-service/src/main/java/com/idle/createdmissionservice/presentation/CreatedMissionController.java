package com.idle.createdmissionservice.presentation;

import com.idle.commonservice.annotation.UserId;
import com.idle.commonservice.base.BaseResponse;
import com.idle.createdmissionservice.application.CreatedMissionService;
import com.idle.createdmissionservice.application.dto.request.CreateMission;
import com.idle.createdmissionservice.application.dto.response.CreatedMissionsView;
import com.idle.createdmissionservice.application.dto.response.MissionAuthenticateView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/created-missions")
public class CreatedMissionController {

    private final CreatedMissionService createdMissionService;

    // 넘어온 날짜에 생성한 미션들 확인
    @GetMapping
    public ResponseEntity<BaseResponse<List<CreatedMissionsView>>> getCreatedMissions(@UserId Long userId, @RequestParam("date") LocalDate date) {
        return ResponseEntity.ok().body(new BaseResponse<>(createdMissionService.getMissionList(date,userId)));
    }

    // 미션 조회 (단건)
    @GetMapping("/{created-mission-id}")
    public ResponseEntity<BaseResponse<CreatedMissionsView>> getCreatedMission(@PathVariable("created-mission-id") Long createdMissionId) {
        return ResponseEntity.ok().body(new BaseResponse<>(createdMissionService.getMission(createdMissionId)));
    }

    // 미션 생성
    @PostMapping
    public void createMission(@UserId Long userId, @RequestBody CreateMission createMission) {
        // TODO: 11/29/24 미션 만들어지는 정책 정해지면 시작
    }

    // 미션 인증
    @PostMapping("/{created-mission-id}")
    public ResponseEntity<BaseResponse<MissionAuthenticateView>> authMission(@UserId Long userId,
                                                                             @PathVariable("created-mission-id") Long createdMissionId,
                                                                             @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok().body(new BaseResponse<>(createdMissionService.authMission(userId , createdMissionId , imageFile)));
    }

    // 해당 날짜에 하나라도 성공한 미션이 있는지 확인

    @GetMapping("/completed-any-mission/{user-id}")
    public boolean getCompletedAnyMission(@PathVariable("user-id") Long userId, @RequestParam("date") LocalDate date) {
        return createdMissionService.getCompletedAnyMission(userId , date);
    }
}
