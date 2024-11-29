package com.idle.createdmissionservice.infrastructure;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.idle.commonservice.exception.BaseException;
import com.idle.commonservice.exception.ErrorCode;
import com.idle.createdmissionservice.application.FileStorageService;
import com.idle.createdmissionservice.application.dto.response.FileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class S3FileStorageService implements FileStorageService {

    private final AmazonS3Client amazonS3Client;
    private final String s3Bucket;
    private final String s3DomainName;
    @Override
    public FileUpload uploadFile(MultipartFile imageFile) {
        //파일의 원본 이름
        String originalFileName = imageFile.getOriginalFilename();
        //DB에 저장될 파일 이름
        String storeFileName = createStoreFileName(originalFileName);

        //S3에 저장
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(imageFile.getContentType());
        metadata.setContentLength(imageFile.getSize());
        try {
            amazonS3Client.putObject(s3Bucket, storeFileName, imageFile.getInputStream(), metadata);
        } catch (IOException e) {
            throw new BaseException(ErrorCode.SERVER_ERROR);
        }
        // 업로드된 파일의 URL 가져오기
        String uploadFileUrl = amazonS3Client.getUrl(s3Bucket, storeFileName).toString();
        return FileUpload.of(originalFileName , s3DomainName + storeFileName , uploadFileUrl);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int post = originalFilename.lastIndexOf(".");
        return originalFilename.substring(post + 1);
    }
}
