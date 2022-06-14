package com.groupprogrammingproject.drive.files.upload;


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

    public String uploadFile(MultipartFile file, String path) throws IOException {
        log.info("Buckets: {}", amazonS3.listBuckets());
        amazonS3.putObject(bucketName, path, file.getInputStream(), new ObjectMetadata());
        return "File saved";
    }
}
