package com.idle.createdmissionservice.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageFileInfo {
    private String originalFileName;
    private String storeFileName;

    public static ImageFileInfo of(String originalFileName , String storeFileName) {
        return ImageFileInfo.builder()
                .originalFileName(originalFileName)
                .storeFileName(storeFileName)
                .build();
    }

}
