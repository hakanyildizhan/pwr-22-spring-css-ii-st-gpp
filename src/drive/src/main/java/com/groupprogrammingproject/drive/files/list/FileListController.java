package com.groupprogrammingproject.drive.files.list;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.groupprogrammingproject.drive.exception.NonexistentObjectException;
import com.groupprogrammingproject.drive.files.dto.FileItem;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileListController {

    public static final String FILES_ENDPOINT = "/files";

    private final FileListService fileListService;

    @GetMapping(value = FILES_ENDPOINT + "/getFiles")
    public ResponseEntity<List<FileItem>> getFiles(@RequestParam("path") String path) {
        try {
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();

            if (path == "." || path == ".\\" || path.isBlank()) {
                path = '/' + userId + '/';
            }
            
            List<FileItem> files = fileListService.getFilesUnderPath(path);
            HttpHeaders headers = new HttpHeaders();
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(files);
        } catch (NonexistentObjectException e) {
            throw new RuntimeException(e);
        }
    }
}
