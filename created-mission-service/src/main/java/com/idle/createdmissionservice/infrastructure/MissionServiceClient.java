package com.idle.createdmissionservice.infrastructure;

import com.idle.createdmissionservice.infrastructure.dto.response.MissionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Mission 바운디드 컨텍스트와 통신할 Client
 */
@FeignClient(name = "mission-service" , url = "http://localhost:8082/api")
public interface MissionServiceClient {
    @GetMapping("/missions")
    List<MissionResponse> getMissionsInfo(@RequestParam("missionId") List<Long> missionIds);
}
