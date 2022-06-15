package com.groupprogrammingproject.drive.files.list;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.groupprogrammingproject.drive.Utils;
import com.groupprogrammingproject.drive.exception.NonexistentObjectException;
import com.groupprogrammingproject.drive.files.dto.FileItem;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileListController {

    public static final String FILES_ENDPOINT = "/files";

    private final FileListService fileListService;

    @GetMapping(value = FILES_ENDPOINT + "/list")
    public ResponseEntity<List<FileItem>> getFilesAndFolders(@RequestParam("parentFolder") String parentFolderId) {
        try {
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            boolean root = false;

            if (Utils.isRootPath(parentFolderId)) {
                parentFolderId = userId;
                root = true;
            }
            
            List<FileItem> files = fileListService.getFilesAndFoldersUnderPath(parentFolderId, root);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(files);
        } catch (NonexistentObjectException e) {
            throw new RuntimeException(e);
        }
    }
}
