package com.groupprogrammingproject.drive.files.download;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileDownloadController {

    public static final String FILES_ENDPOINT = "/files";

    private final FileDownloadService fileDownloadService;

    @GetMapping(value = FILES_ENDPOINT + "/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam("key") String key) {
        try {
            Resource file = fileDownloadService.downloadFile(key);
            if (file == null) {
                log.error("File with key {} was not found.", key);
                return ResponseEntity.internalServerError().body(null);
            }

            ByteArrayResource resource = new ByteArrayResource(file.getInputStream().readAllBytes());
            HttpHeaders headers = new HttpHeaders();
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e) {
            log.error("Error while reading the file with key {}: {}", key, e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
