package com.groupprogrammingproject.drive.authentication.service;

import com.groupprogrammingproject.drive.authentication.dto.AuthenticationRequest;
import com.groupprogrammingproject.drive.authentication.dto.AuthenticationResponse;
import com.groupprogrammingproject.drive.configuration.DriveProperties;
import com.groupprogrammingproject.drive.domain.security.AccountStatus;
import com.groupprogrammingproject.drive.domain.security.AuthorizationData;
import com.groupprogrammingproject.drive.domain.security.AuthorizationDataRepository;

import com.groupprogrammingproject.drive.exception.InactiveAccountException;
import com.groupprogrammingproject.drive.exception.IncorrectPasswordException;
import com.groupprogrammingproject.drive.exception.NonexistentAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.groupprogrammingproject.drive.domain.security.AccountStatus.INACTIVE;

@Service
@RequiredArgsConstructor
public class AuthenticationApplicationService {

    private final AuthorizationDataRepository authorizationDataRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenFactory tokenFactory;

    private final DriveProperties driveProperties;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        AuthorizationData authorizationData = authorizationDataRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(NonexistentAccountException::new);
        throwExceptionForIncorrectPassword(authenticationRequest, authorizationData);
        throwExceptionForInactiveUser(authorizationData);
        String token = generateToken(authorizationData);
        return authenticationResponse(token, authorizationData.getEmail());
    }

    private void throwExceptionForIncorrectPassword(AuthenticationRequest authenticationRequest, AuthorizationData authorizationData) {
        if (!passwordEncoder.matches(authenticationRequest.getPassword(), authorizationData.getPassword())) {
            throw new IncorrectPasswordException();
        }
    }

    private void throwExceptionForInactiveUser(AuthorizationData authorizationData) {
        if (AccountStatus.valueOf(authorizationData.getStatus()) == INACTIVE) {
            throw new InactiveAccountException();
        }
    }

    private AuthenticationResponse authenticationResponse(String token, String userId) {
        return AuthenticationResponse.builder()
                .token(token)
                .userId(userId)
                .expiresIn(3600)
                .build();
    }

    private String generateToken(AuthorizationData authorizationData) {
        return tokenFactory.generateToken(authorizationData.getEmail(), driveProperties.getSecret());
    }
}
