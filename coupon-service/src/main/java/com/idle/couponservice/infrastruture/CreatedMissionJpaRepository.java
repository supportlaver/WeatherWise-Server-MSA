package com.idle.couponservice.infrastruture;

import com.idle.couponservice.domain.createdMission.CreatedMission;
import com.idle.couponservice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatedMissionJpaRepository extends JpaRepository<CreatedMission, Long> {

}
