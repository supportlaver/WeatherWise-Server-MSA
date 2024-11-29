package com.idle.missionservice.presentation;

import com.idle.missionservice.application.MissionService;
import com.idle.missionservice.application.dto.response.MissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/missions")
    public List<MissionResponse> getMissionsInfo(@RequestParam("missionId") List<Long> missionIds) {
        return missionService.getMissionsInfo(missionIds);
    }
}
