package com.idle.createdmissionservice.presentation;

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

    // 생성한 미션 확인
    @GetMapping("/{user-id}")
    public ResponseEntity<BaseResponse<List<CreatedMissionsView>>> getMissions(@PathVariable("user-id") Long userId, @RequestParam("date") LocalDate date) {
        return ResponseEntity.ok().body(new BaseResponse<>(createdMissionService.getMissionList(date,userId)));
    }

    // 미션 생성하기
    // 미션 생성
    @PostMapping("/{user-id}")
    public void createMission(@PathVariable("user-id") Long userId, @RequestBody CreateMission createMission) {
        // TODO: 11/29/24 미션 만들어지는 정책 정해지면 시작
    }

    // 미션 인증
    @PostMapping("/{user-id}/{created-mission-id}")
    public ResponseEntity<BaseResponse<MissionAuthenticateView>> authMission(@PathVariable("user-id") Long userId,
                                                                             @PathVariable("created-mission-id") Long createdMissionId,
                                                                             @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok().body(new BaseResponse<>(createdMissionService.authMission(userId , createdMissionId , imageFile)));
    }


}
