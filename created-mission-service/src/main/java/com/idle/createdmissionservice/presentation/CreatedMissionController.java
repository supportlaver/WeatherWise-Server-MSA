package com.idle.createdmissionservice.presentation;

import com.idle.commonservice.base.BaseResponse;
import com.idle.createdmissionservice.application.CreatedMissionService;
import com.idle.createdmissionservice.application.dto.response.CreatedMissionsView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CreatedMissionController {

    private final CreatedMissionService createdMissionService;
    @GetMapping("/created-missions/{user-id}")
    public ResponseEntity<BaseResponse<List<CreatedMissionsView>>> getMissions(@PathVariable("user-id") Long userId, @RequestParam("date") LocalDate date) {
        return ResponseEntity.ok().body(new BaseResponse<>(createdMissionService.getMissionList(date,userId)));
    }
}
