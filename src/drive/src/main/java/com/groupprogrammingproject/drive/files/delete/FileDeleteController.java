package com.groupprogrammingproject.drive.files.delete;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileDeleteController {

    public static final String FILES_ENDPOINT = "/files";

    private final FileDeleteService fileDeleteService;

    @DeleteMapping(value = FILES_ENDPOINT)
    public void deleteFile(@RequestParam("key") String key) {
        fileDeleteService.deleteFile(key);
    }
}
