package com.groupprogrammingproject.drive.account.service;

import com.groupprogrammingproject.drive.account.AccountRecoveryData;
import com.groupprogrammingproject.drive.account.AccountRecoveryRepository;
import com.groupprogrammingproject.drive.account.dto.*;
import com.groupprogrammingproject.drive.domain.security.AuthorizationData;
import com.groupprogrammingproject.drive.domain.security.AuthorizationDataRepository;
import com.groupprogrammingproject.drive.exception.AccountWithGivenEmailAlreadyExists;
import com.groupprogrammingproject.drive.exception.TokenDoesNotExistOrExpiredException;
import com.groupprogrammingproject.drive.exception.UserDoesNotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static com.groupprogrammingproject.drive.domain.security.AccountStatus.ACTIVE;
import static java.util.UUID.randomUUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountModificationApplicationService {

    private final PasswordEncoder passwordEncoder;

    private final AuthorizationDataRepository authorizationDataRepository;
    private final AccountRecoveryRepository accountRecoveryRepository;

    public AccountCreationResponse createAccount(AccountCreationRequest accountCreationData) {
        log.info("Received account creation request for email {}", accountCreationData.getEmail());
        checkIfAccountWithGivenEmailExists(accountCreationData.getEmail());
        UUID userId = randomUUID();
        AuthorizationData authorizationData = authorizationData(accountCreationData, userId);
        authorizationDataRepository.save(authorizationData);
        log.info("Account has been created for {}. UserId {}", accountCreationData.getEmail(), userId);
        return new AccountCreationResponse(userId);
    }

    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {
        log.info("Forgot password request for email {}", request.getEmail());
        final AuthorizationData user = getUserByEmail(request.getEmail()); // maybe we should not inform that user does not exist?

        final AccountRecoveryData accountRecovery = accountRecoveryRepository.save(new AccountRecoveryData(user.getId()));
        // TODO: send email with token
        final UUID token = accountRecovery.getToken();

        log.info("Forgot password email were send");
        return new ForgotPasswordResponse("We have sent you an e-mail with instruction how to reset your password");
    }

    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        log.info("Reset password request for email {}", request.getToken());
        final AccountRecoveryData recoveryData = getRecoveryDataByToken(UUID.fromString(request.getToken()));
        AuthorizationData user = getUserById(recoveryData.getId());

        // password validation
        new AccountCreationRequest(user.getEmail(), request.getPassword());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        authorizationDataRepository.save(user);

        return new ResetPasswordResponse("Password has been changed");
    }

    private AccountRecoveryData getRecoveryDataByToken(UUID token) throws TokenDoesNotExistOrExpiredException {
        final AccountRecoveryData recoveryData = accountRecoveryRepository.findByToken(token).orElseGet(() -> {
            log.error("Account with given email does not exists");
            throw new TokenDoesNotExistOrExpiredException();
        });
        if (recoveryData.getUsed() || recoveryData.getExpired().before(new Date())) throw new TokenDoesNotExistOrExpiredException();

        return recoveryData;
    }

    private AuthorizationData getUserByEmail(String email) throws UserDoesNotExistException {
        return authorizationDataRepository.findByEmail(email).orElseGet(() -> {
            log.error("Account with given email does not exists");
            throw new UserDoesNotExistException();
        });
    }

    private AuthorizationData getUserById(String id) throws UserDoesNotExistException {
        return authorizationDataRepository.findById(UUID.fromString(id)).orElseGet(() -> {
            log.error("Account with given email does not exists");
            throw new UserDoesNotExistException();
        });
    }

    private void checkIfAccountWithGivenEmailExists(String email) {
        authorizationDataRepository.findByEmail(email)
                .ifPresent(account -> {
                    log.error("Account with given email already exists");
                    throw new AccountWithGivenEmailAlreadyExists();
                });
    }

    private AuthorizationData authorizationData(AccountCreationRequest accountCreationData, UUID userId) {
        return new AuthorizationData(
                userId.toString(),
                accountCreationData.getEmail(),
                passwordEncoder.encode(accountCreationData.getPassword()),
                "ROLE_USER",
                ACTIVE.name());
    }
}
