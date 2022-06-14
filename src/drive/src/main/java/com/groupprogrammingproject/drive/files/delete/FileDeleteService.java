package com.groupprogrammingproject.drive.files.delete;


import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileDeleteService {

    private final AmazonS3 amazonS3;

    @Value("${amazon.s3.bucket}")
    private String bucketName;

    public void deleteFile(String key) {
        try {
            amazonS3.deleteObject(bucketName, key);
        } catch (Exception e) {
            log.error("Error while deleting item from bucket: {0]", e.getMessage());
        }
    }
}
