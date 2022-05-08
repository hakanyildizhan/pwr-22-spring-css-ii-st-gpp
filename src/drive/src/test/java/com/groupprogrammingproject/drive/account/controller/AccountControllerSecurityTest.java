package com.groupprogrammingproject.drive.account.controller;

import com.groupprogrammingproject.drive.ContextTestBase;
import com.groupprogrammingproject.drive.account.dto.AccountCreationRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.groupprogrammingproject.drive.account.controller.AccountController.ACCOUNTS;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerSecurityTest extends ContextTestBase {

    @Test
    void shouldAllowAllUsersRegister() throws Exception {
        mockMvc.perform(post(ACCOUNTS)
                        .content(accountCreationRequest())
                        .header(CONTENT_TYPE, APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    private String accountCreationRequest() throws IOException {
        AccountCreationRequest request = AccountCreationRequest.builder()
                .email("unauthorized@gmail.com")
                .password("unauthorized")
                .build();
        return objectMapper.writeValueAsString(request);
    }
}
