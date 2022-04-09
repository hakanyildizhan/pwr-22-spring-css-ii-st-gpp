package com.groupprogrammingproject.drive.authentication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.groupprogrammingproject.drive.ContextTestBase;
import com.groupprogrammingproject.drive.account.dto.AccountCreationRequest;
import com.groupprogrammingproject.drive.authentication.dto.AuthenticationRequest;
import org.junit.jupiter.api.Test;

import static com.groupprogrammingproject.drive.exception.ExceptionCode.INCORRECT_PASSWORD;
import static com.groupprogrammingproject.drive.exception.ExceptionCode.NONEXISTENT_ACCOUNT;
import static com.groupprogrammingproject.drive.exception.ExceptionMessage.INCORRECT_PASSWORD_MESSAGE;
import static com.groupprogrammingproject.drive.exception.ExceptionMessage.NONEXISTENT_ACCOUNT_MESSAGE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerComponentTest extends ContextTestBase {

    private static final String EMAIL = "ttester@gmail.com";

    private static final String PASSWORD = "tester";

    @Test
    void shouldAuthenticateUser() throws Exception {
        postRequest("/accounts", accountCreationRequest());

        postRequest("/login", authenticationRequest())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.userId").exists());
    }

    @Test
    void shouldReturnBadRequestWhenAccountDoesNotExist() throws Exception {
        postRequest("/login", authenticationRequest("nonexistent@email.com", "password"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(NONEXISTENT_ACCOUNT))
                .andExpect(jsonPath("$.message").value(NONEXISTENT_ACCOUNT_MESSAGE));
    }

    @Test
    void shouldReturnBadRequestWhenPasswordIsIncorrect() throws Exception {
        postRequest("/accounts", accountCreationRequest());

        postRequest("/login", authenticationRequest(EMAIL, "password"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(INCORRECT_PASSWORD))
                .andExpect(jsonPath("$.message").value(INCORRECT_PASSWORD_MESSAGE));
    }

    @Test
    void shouldReturnBadRequestWhenEmailIsNull() throws Exception {
        postRequest("/login", authenticationRequest(null, "password"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPasswordIsNull() throws Exception {
        postRequest("/login", authenticationRequest("nonexistent@email.com", null))
                .andExpect(status().isBadRequest());
    }

    private String accountCreationRequest() throws JsonProcessingException {
        AccountCreationRequest accountCreationRequest = AccountCreationRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        return objectMapper.writeValueAsString(accountCreationRequest);
    }

    private String authenticationRequest(String email, String password) throws JsonProcessingException {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email(email)
                .password(password)
                .build();
        return objectMapper.writeValueAsString(authenticationRequest);
    }

    private String authenticationRequest() throws JsonProcessingException {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        return objectMapper.writeValueAsString(authenticationRequest);
    }
}
