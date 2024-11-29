package com.idle.createdmissionservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleMission {
    private Long id;
    private String name;
    private boolean isCompleted;
    private int point;
}
