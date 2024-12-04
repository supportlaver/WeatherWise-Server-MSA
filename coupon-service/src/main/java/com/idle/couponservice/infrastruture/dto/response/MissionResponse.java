package com.idle.couponservice.infrastruture.dto.response;

import lombok.*;

@Builder
@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class MissionResponse {
    private Long missionId;
    private String name;
    private String question;
    private int exp;
}
