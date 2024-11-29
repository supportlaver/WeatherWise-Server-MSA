package com.idle.createdmissionservice.application;

import com.idle.createdmissionservice.application.dto.response.FileUpload;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    FileUpload uploadFile(MultipartFile file);
}

