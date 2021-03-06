package com.groupprogrammingproject.drive.account.controller;

import com.groupprogrammingproject.drive.account.dto.AccountCreationRequest;
import com.groupprogrammingproject.drive.account.dto.AccountCreationResponse;
import com.groupprogrammingproject.drive.account.service.AccountModificationApplicationService;
import com.groupprogrammingproject.drive.exception.AccountWithGivenEmailAlreadyExists;
import com.groupprogrammingproject.drive.exception.ErrorBody;
import com.groupprogrammingproject.drive.exception.ExceptionCode;
import com.groupprogrammingproject.drive.exception.UserDoesNotExistException;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.groupprogrammingproject.drive.exception.ExceptionCode.DATA_INTEGRITY;
import static com.groupprogrammingproject.drive.exception.ExceptionCode.NONEXISTENT_ACCOUNT;
import static com.groupprogrammingproject.drive.exception.ExceptionMessage.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    public static final String ACCOUNTS = "/accounts";

    public static final String ACCOUNT_DETAILS_URL = ACCOUNTS + "/{userId}";

    private final AccountModificationApplicationService accountModificationApplicationService;

    @PostMapping(ACCOUNTS)
    @ResponseStatus(CREATED)
    public AccountCreationResponse createAccount(@RequestBody @Valid AccountCreationRequest accountCreationRequest) {
        return accountModificationApplicationService.createAccount(accountCreationRequest);
    }

    @GetMapping(ACCOUNTS)
    public String activateAccount(@RequestParam("action") String action, @RequestParam("activationId") String activationId) {
        if (action.equals("activation")) {
            accountModificationApplicationService.activateAccount(activationId);
            return "Account activated";
        }
        return "Incorrect action";
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorBody handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        return ErrorBody.builder()
                .message(DATA_INTEGRITY_MESSAGE)
                .originalMessage(exception.getMessage())
                .code(DATA_INTEGRITY)
                .build();
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(UserDoesNotExistException.class)
    public ErrorBody handleUserDoesNotExistException(UserDoesNotExistException exception) {
        return ErrorBody.builder()
                .message(NONEXISTENT_ACCOUNT_MESSAGE)
                .code(NONEXISTENT_ACCOUNT)
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(AccountWithGivenEmailAlreadyExists.class)
    public ErrorBody handleUserDoesNotExistException(AccountWithGivenEmailAlreadyExists exception) {
        return ErrorBody.builder()
                .message(ACCOUNT_ALREADY_EXISTS)
                .code(ExceptionCode.ACCOUNT_ALREADY_EXISTS)
                .build();
    }
}
