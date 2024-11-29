package com.idle.missionservice.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class MissionInfo {
    private String name;
    private String description;
    private String englishQuestion;

    public static MissionInfo of(String name  , String description , String englishQuestion) {
        return MissionInfo.builder()
                .name(name)
                .description(description)
                .englishQuestion(englishQuestion)
                .build();
    }
}
