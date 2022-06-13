package com.groupprogrammingproject.drive.authentication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.groupprogrammingproject.drive.ContextTestBase;
import com.groupprogrammingproject.drive.account.dto.AccountCreationRequest;
import com.groupprogrammingproject.drive.authentication.dto.AuthenticationRequest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerSecurityTest extends ContextTestBase {

    private static final String EMAIL = "sectester@gmail.com";

    private static final String PASSWORD = "sectester";

    @Test
    void shouldAllowAllUsersAuthenticate() throws Exception {
        postRequest("/accounts", accountCreationRequest());

        postRequest("/login", authenticationRequest())
                .andExpect(status().isOk());
    }

    private String accountCreationRequest() throws JsonProcessingException {
        AccountCreationRequest accountCreationRequest = AccountCreationRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        return objectMapper.writeValueAsString(accountCreationRequest);
    }

    private String authenticationRequest() throws JsonProcessingException {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(EMAIL, PASSWORD);
        return objectMapper.writeValueAsString(authenticationRequest);
    }
}
