package com.groupprogrammingproject.drive.account.service;

import com.groupprogrammingproject.drive.account.AccountRecoveryRepository;
import com.groupprogrammingproject.drive.account.dto.AccountCreationRequest;
import com.groupprogrammingproject.drive.account.dto.AccountCreationResponse;
import com.groupprogrammingproject.drive.domain.security.AuthorizationData;
import com.groupprogrammingproject.drive.domain.security.AuthorizationDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AccountModificationApplicationServiceTest {

    private static final String EMAIL = "testing@tester.com";

    private static final String PASSWORD = "test-password";

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    private AuthorizationDataRepository authorizationDataRepository;

    @Mock
    private AccountRecoveryRepository accountRecoveryRepository;

    @InjectMocks
    private AccountModificationApplicationService accountModificationApplicationService;

    @BeforeEach
    void setUp() {
        accountModificationApplicationService = new AccountModificationApplicationService(
                passwordEncoder,
                authorizationDataRepository,
                accountRecoveryRepository
        );
    }

    @Test
    void shouldCreateAccount() {
        //when
        AccountCreationResponse actualResponse = accountModificationApplicationService.createAccount(accountCreationRequest());

        //then
        verify(authorizationDataRepository, times(1)).save(any(AuthorizationData.class));
        assertThat(actualResponse.getUserId()).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenAccountExists() {
        //given
        given(authorizationDataRepository.save(any(AuthorizationData.class))).willThrow(DataIntegrityViolationException.class);

        //when
        Throwable exception = catchThrowable(() -> accountModificationApplicationService.createAccount(accountCreationRequest()));

        //then
        verify(authorizationDataRepository, times(1)).save(any(AuthorizationData.class));
        assertThat(exception.getClass()).isEqualTo(DataIntegrityViolationException.class);
    }


    private AccountCreationRequest accountCreationRequest() {
        return AccountCreationRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }
}
