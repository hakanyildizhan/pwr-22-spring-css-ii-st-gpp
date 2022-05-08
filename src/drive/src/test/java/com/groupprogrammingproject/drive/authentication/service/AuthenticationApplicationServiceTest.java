package com.groupprogrammingproject.drive.authentication.service;

import com.groupprogrammingproject.drive.authentication.dto.AuthenticationRequest;
import com.groupprogrammingproject.drive.authentication.dto.AuthenticationResponse;
import com.groupprogrammingproject.drive.configuration.DriveProperties;
import com.groupprogrammingproject.drive.domain.security.AuthorizationData;
import com.groupprogrammingproject.drive.domain.security.AuthorizationDataRepository;
import com.groupprogrammingproject.drive.exception.IncorrectPasswordException;
import com.groupprogrammingproject.drive.exception.NonexistentAccountException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.groupprogrammingproject.drive.domain.security.AccountStatus.ACTIVE;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class AuthenticationApplicationServiceTest {

    private static final String EMAIL = "test@test.com";

    private static final String PASSWORD = "password";

    private static final String TOKEN = "token";

    private static final String SECRET = "test-secret";

    private static final String ENCRYPTED_PASSWORD = new BCryptPasswordEncoder().encode(PASSWORD);

    @Mock
    private AuthorizationDataRepository authorizationDataRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenFactory tokenFactory;

    @Mock
    private DriveProperties matchifyProperties;

    @InjectMocks
    private AuthenticationApplicationService authenticationApplicationService;

    @Test
    void shouldAuthenticateUser() {
        given(matchifyProperties.getSecret()).willReturn(SECRET);
        given(tokenFactory.generateToken(EMAIL, SECRET)).willReturn(TOKEN);
        given(passwordEncoder.matches(PASSWORD, ENCRYPTED_PASSWORD)).willReturn(true);
        given(authorizationDataRepository.findByEmail(EMAIL)).willReturn(Optional.of(authorizationData()));

        AuthenticationResponse authenticationResponse = authenticationApplicationService.authenticate(authenticationRequest());

        assertThat(authenticationResponse.getToken()).isEqualTo(TOKEN);
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsIncorrect() {
        given(passwordEncoder.matches(PASSWORD, ENCRYPTED_PASSWORD)).willReturn(false);
        given(authorizationDataRepository.findByEmail(EMAIL)).willReturn(Optional.of(authorizationData()));

        Throwable exception = catchThrowable(() -> authenticationApplicationService.authenticate(authenticationRequest()));

        assertThat(exception.getClass()).isEqualTo(IncorrectPasswordException.class);
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {
        given(authorizationDataRepository.findByEmail(EMAIL)).willReturn(Optional.empty());

        Throwable exception = catchThrowable(() -> authenticationApplicationService.authenticate(authenticationRequest()));

        verifyNoMoreInteractions(authorizationDataRepository);
        assertThat(exception.getClass()).isEqualTo(NonexistentAccountException.class);
    }

    private AuthenticationRequest authenticationRequest() {
        return AuthenticationRequest.builder().email(EMAIL).password(PASSWORD).build();
    }

    private AuthorizationData authorizationData() {
        return new AuthorizationData(
                randomUUID().toString(),
                EMAIL,
                ENCRYPTED_PASSWORD,
                "ROLE_USER",
                ACTIVE.name());
    }
}
