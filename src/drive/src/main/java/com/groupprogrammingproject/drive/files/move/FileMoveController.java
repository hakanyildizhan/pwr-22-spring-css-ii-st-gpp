package com.groupprogrammingproject.drive.files.move;

import com.groupprogrammingproject.drive.exception.ErrorBody;
import com.groupprogrammingproject.drive.exception.NonexistentObjectException;
import com.groupprogrammingproject.drive.files.move.dto.FileMoveRequest;
import com.groupprogrammingproject.drive.files.move.dto.FileMoveResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import javax.validation.Valid;

import static com.groupprogrammingproject.drive.exception.ExceptionCode.NONEXISTENT_FOLDER_CODE;
import static com.groupprogrammingproject.drive.exception.ExceptionMessage.NONEXISTENT_FOLDER_MESSAGE;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileMoveController {

    public static final String FILES_ENDPOINT = "/files";

    private final FileMoveService fileMoveService;

    @PostMapping(value = FILES_ENDPOINT + "/moveItem")
    public FileMoveResponse fileMove(@RequestBody @Valid FileMoveRequest fileMoveRequest) {
        return fileMoveService.move(fileMoveRequest);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NonexistentObjectException.class)
    public ErrorBody handleNonexistentAccountException(NonexistentObjectException exception) {
        return ErrorBody.builder()
                .message(NONEXISTENT_FOLDER_MESSAGE)
                .originalMessage(exception.getMessage())
                .code(NONEXISTENT_FOLDER_CODE)
                .build();
    }
}
