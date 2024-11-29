package com.idle.createdmissionservice.infrastructure.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAcquisitionExpResponse {
    private Long userId;
    private int level;
    private String nickName;
    private int exp;
}
