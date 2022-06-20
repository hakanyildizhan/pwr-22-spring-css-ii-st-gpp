package com.groupprogrammingproject.drive.files.share;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupprogrammingproject.drive.domain.file.share.LinkFileShare;
import com.groupprogrammingproject.drive.domain.file.share.PersonalFileShare;
import com.groupprogrammingproject.drive.exception.ErrorBody;
import com.groupprogrammingproject.drive.exception.NonexistentAccountException;
import com.groupprogrammingproject.drive.exception.NonexistentObjectException;
import com.groupprogrammingproject.drive.exception.UnauthorizedFileAccessException;
import com.groupprogrammingproject.drive.files.share.dto.LinkShareFileRequest;
import com.groupprogrammingproject.drive.files.share.dto.LinkShareFileResponse;
import com.groupprogrammingproject.drive.files.share.dto.PersonalShareFileRequest;
import com.groupprogrammingproject.drive.files.share.dto.PersonalShareFileResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.groupprogrammingproject.drive.exception.ExceptionCode.*;
import static com.groupprogrammingproject.drive.exception.ExceptionMessage.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
public class ShareFileController {

    private final ShareFileService shareFileService;

    private final ObjectMapper objectMapper;

    @PutMapping("/files/{fileId}/link-access")
    public LinkShareFileResponse shareFileByLink(@RequestBody @Valid LinkShareFileRequest request, @PathVariable("fileId") String fileId) {
        LinkFileShare linkFileShare = shareFileService.shareFile(fileId, request.accessType());
        return LinkShareFileResponse.fromDomain(linkFileShare);
    }

    @PutMapping("/files/{fileId}/personal-access")
    public PersonalShareFileResponse shareFileWithPeople(@RequestBody @Valid PersonalShareFileRequest request, @PathVariable("fileId") String fileId) {
        try {
            PersonalFileShare personalFileShare = shareFileService.shareFileWithPeople(fileId, request.personalAccessByEmail());
            return PersonalShareFileResponse.fromDomain(personalFileShare, objectMapper);
        } catch (JsonProcessingException e) {
            log.error("Could not process personal access entity");
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NonexistentAccountException.class)
    public ErrorBody handleNonexistentAccountException(NonexistentAccountException exception) {
        return ErrorBody.builder()
                .message(NONEXISTENT_ACCOUNT_MESSAGE)
                .originalMessage(exception.getMessage())
                .code(NONEXISTENT_ACCOUNT)
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(NonexistentObjectException.class)
    public ErrorBody handleNonexistentObjectException(NonexistentObjectException exception) {
        return ErrorBody.builder()
                .message(NONEXISTENT_OBJECT_MESSAGE)
                .originalMessage(exception.getMessage())
                .code(NONEXISTENT_OBJECT)
                .build();
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
