package com.idle.couponservice.infrastruture;

import com.idle.commonservice.annotation.UserId;
import com.idle.couponservice.infrastruture.dto.response.UserCompletedAnyMissionResponse;
import com.idle.couponservice.infrastruture.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;


/**
 * CreatedMission 바운디드 컨텍스트와 통신할 Client
 */
@FeignClient(name = "created-mission-service" , url = "http://localhost:8083/api/created-missions")
public interface CreatedMissionServiceClient {
    @GetMapping("/completed-any-mission/{user-id}")
    boolean hasUserCompletedAnyMission(@PathVariable("user-id") Long userId , @RequestParam("date") LocalDate date);
}
