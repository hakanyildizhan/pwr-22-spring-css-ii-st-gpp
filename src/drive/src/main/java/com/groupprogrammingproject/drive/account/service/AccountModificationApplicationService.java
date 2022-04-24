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

import java.util.UUID;

import static com.groupprogrammingproject.drive.domain.security.AccountStatus.ACTIVE;
import static java.util.UUID.randomUUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountModificationApplicationService {

    private final PasswordEncoder passwordEncoder;

    private final AuthorizationDataRepository authorizationDataRepository;

    public AccountCreationResponse createAccount(AccountCreationRequest accountCreationData) {
        log.info("Received account creation request for email {}", accountCreationData.getEmail());
        checkIfAccountWithGivenEmailExists(accountCreationData.getEmail());
        UUID userId = randomUUID();
        AuthorizationData authorizationData = authorizationData(accountCreationData, userId);
        authorizationDataRepository.save(authorizationData);
        log.info("Account has been created for {}. UserId {}", accountCreationData.getEmail(), userId);
        return new AccountCreationResponse(userId);
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
