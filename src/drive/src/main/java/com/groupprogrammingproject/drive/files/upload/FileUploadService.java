package com.groupprogrammingproject.drive.files.upload;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final AmazonS3 amazonS3;

    public String uploadFile(MultipartFile file) throws IOException {
        log.info("Buckets: {}", amazonS3.listBuckets());
        amazonS3.putObject("sample", file.getName(), file.getInputStream(), new ObjectMetadata());
        return "File saved";
    }
}
