package com.groupprogrammingproject.drive.files.upload;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    public static final String FILES_ENDPOINT = "/files";

    private final FileUploadService fileUploadService;

    @PostMapping(FILES_ENDPOINT)
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        try {
            return this.fileUploadService.uploadFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
