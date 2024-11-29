package com.idle.createdmissionservice.infrastructure.dto.response;

import lombok.*;

import java.util.List;

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
