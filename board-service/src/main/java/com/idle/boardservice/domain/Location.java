package com.idle.boardservice.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter @AllArgsConstructor
@NoArgsConstructor @Builder
public class Location {
    private String locationName;
    private Double latitude;
    private Double longitude;

    public static Location of(String locationName , Double latitude , Double longitude) {
        return Location.builder()
                .locationName(locationName)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
