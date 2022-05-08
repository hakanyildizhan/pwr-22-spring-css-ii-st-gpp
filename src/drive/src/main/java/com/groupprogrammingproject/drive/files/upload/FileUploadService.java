package com.groupprogrammingproject.drive.files.upload;


import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final AmazonS3 amazonS3;

    public void putObject() {
        log.info("Buckets: {}", amazonS3.listBuckets());
        amazonS3.putObject("sample", "files/sample.txt", "src/test-file.txt");
    }
}
