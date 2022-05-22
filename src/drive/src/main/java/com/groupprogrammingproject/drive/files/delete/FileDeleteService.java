package com.groupprogrammingproject.drive.files.delete;


import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileDeleteService {

    private final AmazonS3 amazonS3;

    public void deleteFile(String key) {
        amazonS3.deleteObject("sample", key);
    }
}
