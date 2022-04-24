package com.groupprogrammingproject.drive.files.upload;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    public static final String ENDPOINT = "/files";

    private final FileUploadService fileUploadService;

    @GetMapping(ENDPOINT)
    public void putObject() {
        fileUploadService.putObject();
    }
}
