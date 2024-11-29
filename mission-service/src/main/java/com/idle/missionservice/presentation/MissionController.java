package com.idle.missionservice.presentation;

import com.idle.missionservice.application.MissionService;
import com.idle.missionservice.application.dto.response.MissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    public List<MissionResponse> getMissionsInfo(@RequestParam("missionId") List<Long> missionIds) {
        return missionService.getMissionsInfo(missionIds);
    }

    @GetMapping("/{mission-id}")
    public MissionResponse getMissionInfo(@PathVariable("mission-id") Long missionId) {
        return missionService.getMissionInfo(missionId);
    }
}
