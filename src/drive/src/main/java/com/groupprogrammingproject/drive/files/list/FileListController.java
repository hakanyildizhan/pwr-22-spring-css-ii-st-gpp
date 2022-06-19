package com.groupprogrammingproject.drive.files.list;

import com.groupprogrammingproject.drive.exception.ErrorBody;
import com.groupprogrammingproject.drive.exception.UnauthorizedFileAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.groupprogrammingproject.drive.Utils;
import com.groupprogrammingproject.drive.exception.NonexistentObjectException;
import com.groupprogrammingproject.drive.files.dto.FileItem;

import java.util.List;

import static com.groupprogrammingproject.drive.exception.ExceptionCode.UNAUTHORIZED_FILE_ACCESS;
import static com.groupprogrammingproject.drive.exception.ExceptionMessage.UNAUTHORIZED_FILE_ACCESS_MESSAGE;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequiredArgsConstructor
public class FileListController {

    public static final String FILES_ENDPOINT = "/files";

    private final FileListService fileListService;

    @PostMapping(value = FILES_ENDPOINT + "/list")
    public ResponseEntity<List<FileItem>> getFilesAndFolders(@RequestPart("parentFolder") String parentFolderId) {
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

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(UnauthorizedFileAccessException.class)
    public ErrorBody handleUnauthorizedFileAccessException(UnauthorizedFileAccessException exception) {
        return ErrorBody.builder()
                .message(UNAUTHORIZED_FILE_ACCESS_MESSAGE)
                .originalMessage(exception.getMessage())
                .code(UNAUTHORIZED_FILE_ACCESS)
                .build();
    }
}
