package com.groupprogrammingproject.drive.account.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.groupprogrammingproject.drive.ContextTestBase;
import com.groupprogrammingproject.drive.account.dto.AccountCreationRequest;
import com.groupprogrammingproject.drive.exception.ExceptionCode;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.groupprogrammingproject.drive.exception.ExceptionMessage.ACCOUNT_ALREADY_EXISTS;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AccountControllerComponentTest extends ContextTestBase {

    private static final String EMAIL = "marcinuminski1998@gmail.com";

    private static final String INVALID_EMAIL = "invalid@email.com";

    private static final String PASSWORD = "XXXxxx###4444";

    @Test
    void shouldReturn201WhenCreatingAccount() throws Exception {
        mockMvc.perform(post("/accounts")
                .content(accountCreationRequest())
                .header(CONTENT_TYPE, APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenCreatingAccountWithExistingEmail() throws Exception {
        mockMvc.perform(post("/accounts")
                .content(accountCreationRequest(INVALID_EMAIL, PASSWORD))
                .header(CONTENT_TYPE, APPLICATION_JSON));

        mockMvc.perform(post("/accounts")
                .content(accountCreationRequest(INVALID_EMAIL, PASSWORD))
                .header(CONTENT_TYPE, APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ExceptionCode.ACCOUNT_ALREADY_EXISTS))
                .andExpect(jsonPath("$.message").value(ACCOUNT_ALREADY_EXISTS));
    }
    @Test
    void shouldReturn400WhenCreatingAccountWithoutEmail() throws Exception {
        mockMvc.perform(post("/accounts")
                .content(invalidAccountCreationRequest(null, PASSWORD))
                .header(CONTENT_TYPE, APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenCreatingAccountWithInvalidEmail() throws Exception {
        mockMvc.perform(post("/accounts")
                .content(invalidAccountCreationRequest(null, PASSWORD))
                .header(CONTENT_TYPE, APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenCreatingAccountWithoutPassword() throws Exception {
        mockMvc.perform(post("/accounts")
                .content(invalidAccountCreationRequest(EMAIL, ""))
                .header(CONTENT_TYPE, APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private String accountCreationRequest() throws IOException {
        AccountCreationRequest request = objectMapper.readValue(new File("src/test/resources/stubs/account-creation.json"), AccountCreationRequest.class);
        return objectMapper.writeValueAsString(request);
    }

    private String accountCreationRequest(String email, String password) throws JsonProcessingException {
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest(email, password);
        return objectMapper.writeValueAsString(accountCreationRequest);
    }

    private String invalidAccountCreationRequest(String email, String password) throws JsonProcessingException {
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest(email, password);
        return objectMapper.writeValueAsString(accountCreationRequest);
    }
}
