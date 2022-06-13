package com.groupprogrammingproject.drive.authentication.controller;

import com.groupprogrammingproject.drive.authentication.dto.AuthenticationRequest;
import com.groupprogrammingproject.drive.authentication.dto.AuthenticationResponse;
import com.groupprogrammingproject.drive.authentication.service.AuthenticationApplicationService;
import com.groupprogrammingproject.drive.exception.InactiveAccountException;
import com.groupprogrammingproject.drive.exception.ErrorBody;
import com.groupprogrammingproject.drive.exception.IncorrectPasswordException;
import com.groupprogrammingproject.drive.exception.NonexistentAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.groupprogrammingproject.drive.exception.ExceptionCode.*;
import static com.groupprogrammingproject.drive.exception.ExceptionMessage.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class AuthenticationController {

    private final AuthenticationApplicationService authenticationApplicationService;

    @PostMapping
    public AuthenticationResponse authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return authenticationApplicationService.authenticate(authenticationRequest);
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectPasswordException.class)
    public ErrorBody handleIncorrectPasswordException(IncorrectPasswordException exception) {
        return ErrorBody.builder()
                .message(INCORRECT_PASSWORD_MESSAGE)
                .originalMessage(exception.getMessage())
                .code(INCORRECT_PASSWORD)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InactiveAccountException.class)
    public ErrorBody handleBlockedAccountException(InactiveAccountException exception) {
        return ErrorBody.builder()
                .code(ACCOUNT_IS_BLOCKED)
                .message(ACCOUNT_INACTIVE_MESSAGE)
                .build();
    }
}
