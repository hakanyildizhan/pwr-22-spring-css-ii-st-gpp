package com.groupprogrammingproject.drive.account.service;

import com.groupprogrammingproject.drive.account.dto.AccountCreationRequest;
import com.groupprogrammingproject.drive.account.dto.AccountCreationResponse;
import com.groupprogrammingproject.drive.domain.security.AuthorizationData;
import com.groupprogrammingproject.drive.domain.security.AuthorizationDataRepository;
import com.groupprogrammingproject.drive.exception.AccountWithGivenEmailAlreadyExists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.groupprogrammingproject.drive.domain.security.AccountStatus.ACTIVE;
import static com.groupprogrammingproject.drive.domain.security.AccountStatus.INACTIVE;
import static java.util.UUID.randomUUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountModificationApplicationService {

    private final PasswordEncoder passwordEncoder;

    private final AuthorizationDataRepository authorizationDataRepository;

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
