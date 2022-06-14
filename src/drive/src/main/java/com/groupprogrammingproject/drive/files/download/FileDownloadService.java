package com.groupprogrammingproject.drive.files.download;


import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileDownloadService {

    private final AmazonS3 amazonS3;

    @Value("${amazon.s3.bucket}")
    private String bucketName;

    public Resource downloadFile(String key) throws IOException {
        return new ByteArrayResource(amazonS3.getObject(bucketName, key).getObjectContent().readAllBytes());
    }
}
