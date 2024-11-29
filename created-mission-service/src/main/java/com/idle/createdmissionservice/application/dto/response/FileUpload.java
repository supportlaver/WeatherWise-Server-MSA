package com.idle.createdmissionservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class FileUpload {
    // 파일 원본 이름
    private String originalFileName;
    // DB 에 저장될 파일 이름
    private String storeFileName;
    // 업로드된 파일 URL
    private String uploadFileUrl;

    public static FileUpload of(String originalFileName , String storeFileName , String uploadFileUrl) {
        return FileUpload.builder()
                .originalFileName(originalFileName)
                .storeFileName(storeFileName)
                .uploadFileUrl(uploadFileUrl)
                .build();
    }
}
