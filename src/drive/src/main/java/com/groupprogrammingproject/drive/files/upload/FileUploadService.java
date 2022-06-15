package com.groupprogrammingproject.drive.files.upload;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final AmazonS3 amazonS3;

    @Value("${amazon.s3.bucket}")
    private String bucketName;

    public boolean uploadFile(MultipartFile file, String path) throws IOException {
        log.info("Buckets: {}", amazonS3.listBuckets());
        
        try {
            amazonS3.putObject(bucketName, path, file.getInputStream(), new ObjectMetadata());
            return true;
        } catch (AmazonServiceException e) {
            log.error("Error while uploading file. BucketName: {}, Path: {}, FileName: {}", 
                bucketName, 
                path, 
                file.getOriginalFilename());

            return false;
        }
    }
}
