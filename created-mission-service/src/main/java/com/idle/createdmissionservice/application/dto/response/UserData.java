package com.idle.createdmissionservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder @Getter
@NoArgsConstructor @AllArgsConstructor
public class UserData {
    private Long id;
    private String nickName;

    public static UserData of(Long id , String nickName) {
        return UserData.builder()
                .id(id)
                .nickName(nickName)
                .build();
    }


}
