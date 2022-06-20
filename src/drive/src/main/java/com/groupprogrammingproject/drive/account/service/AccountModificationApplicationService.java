package com.groupprogrammingproject.drive.account.service;

import com.groupprogrammingproject.drive.account.dto.*;
import com.groupprogrammingproject.drive.domain.security.*;
import com.groupprogrammingproject.drive.exception.AccountWithGivenEmailAlreadyExists;
import com.groupprogrammingproject.drive.exception.TokenDoesNotExistOrExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.groupprogrammingproject.drive.domain.security.AccountStatus.ACTIVE;
import static com.groupprogrammingproject.drive.domain.security.AccountStatus.INACTIVE;
import static java.util.UUID.randomUUID;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountModificationApplicationService {

    private final PasswordEncoder passwordEncoder;

    private final AuthorizationDataRepository authorizationDataRepository;

    private final RecoveryTokenRepository recoveryTokenRepository;

    private final AccountCreationConfirmationEmailSender emailSender;

    public AccountCreationResponse createAccount(AccountCreationRequest accountCreationData) {
        log.info("Received account creation request for email {}", accountCreationData.getEmail());
        checkIfAccountWithGivenEmailExists(accountCreationData.getEmail());
        UUID userId = randomUUID();
        AuthorizationData authorizationData = inactiveAuthorizationData(accountCreationData, userId);
        authorizationDataRepository.save(authorizationData);
        log.info("Account has been created for {}. UserId {}", accountCreationData.getEmail(), userId);
        emailSender.sendSimpleMessage(authorizationData.getEmail(), authorizationData.getActivationId());
        return new AccountCreationResponse(userId);
    }
    @Transactional
    public void activateAccount(String activationId) {
        authorizationDataRepository.findAll()
                .forEach(account -> {
                    if (account.getActivationId().equals(activationId)) {
                        account.setStatus(ACTIVE.name());
                        authorizationDataRepository.save(account);
                        log.info("Account {} activated", account.getId());
                    }
                });
    }

    @Transactional
    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {
        log.info("Forgot password request for email {}", request.getEmail());
        final AuthorizationData user = getUserByEmail(request.getEmail());
        if (user == null) {
            log.info("Forgot password email was fake");
            return new ForgotPasswordResponse("We have sent you an e-mail with instruction how to reset your password");
        }

        List<RecoveryToken> oldTokens = recoveryTokenRepository.findAllByEmail(request.getEmail()).get().stream().filter(elem -> elem.getStatus() == RecoveryTokenStatus.ACTIVE.ordinal()).toList();
        for (RecoveryToken t : oldTokens) {
            t.setStatus(RecoveryTokenStatus.INACTIVE.ordinal());
            recoveryTokenRepository.save(t);
        }

        final RecoveryToken recoveryToken = recoveryTokenRepository.save(new RecoveryToken(user.getEmail()));

        final String token = recoveryToken.getId();
        emailSender.sendRecoveryToken(user.getEmail(), token);

        log.info("Forgot password email were send");
        return new ForgotPasswordResponse("We have sent you an e-mail with instruction how to reset your password");
    }

    @Transactional
    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        log.info("Reset password request for email {}", request.getToken());
        final RecoveryToken recoveryToken = recoveryTokenRepository.findById(request.getToken()).get();

        if (recoveryToken.getStatus() != RecoveryTokenStatus.ACTIVE.ordinal() || recoveryToken.getExpired().before(new Date())) throw new TokenDoesNotExistOrExpiredException();

        AuthorizationData user = getUserByEmail(recoveryToken.getEmail());

        recoveryToken.setStatus(RecoveryTokenStatus.USED);
        recoveryTokenRepository.save(recoveryToken);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        authorizationDataRepository.save(user);

        return new ResetPasswordResponse("Password has been changed");
    }

    public AuthorizationData getUserByEmail(String email) {
        Optional<AuthorizationData> account = authorizationDataRepository.findByEmail(email);

        if (account != null) {
            return account.get();
        } else {
            return null;
        }
    }

    private void checkIfAccountWithGivenEmailExists(String email) {
        authorizationDataRepository.findByEmail(email)
                .ifPresent(account -> {
                    log.error("Account with given email already exists");
                    throw new AccountWithGivenEmailAlreadyExists();
                });
    }

    private AuthorizationData inactiveAuthorizationData(AccountCreationRequest accountCreationData, UUID userId) {
        return new AuthorizationData(
                userId.toString(),
                accountCreationData.getEmail(),
                passwordEncoder.encode(accountCreationData.getPassword()),
                "ROLE_USER",
                INACTIVE.name(),
                UUID.randomUUID().toString());
    }
}
