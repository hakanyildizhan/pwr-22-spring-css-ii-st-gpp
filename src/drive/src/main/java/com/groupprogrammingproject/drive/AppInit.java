package com.groupprogrammingproject.drive;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppInit implements ApplicationRunner  {
    
    private final AmazonS3 amazonS3;

    @Value("${amazon.s3.bucket}")
    private String bucketName;

    public void run(ApplicationArguments args) {
        try {
            amazonS3.createBucket(bucketName);
        } catch (AmazonServiceException e) {
            log.error("Error while creating bucket during initialization: {}", e.getErrorMessage());
        }
    }
}