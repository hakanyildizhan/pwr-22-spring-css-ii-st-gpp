package com.groupprogrammingproject.drive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupprogrammingproject.drive.domain.security.AuthorizationDataRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ContextTestBase {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected AuthorizationDataRepository authorizationDataRepository;


    protected ResultActions postRequest(String url, String requestBody) throws Exception {
        return mockMvc.perform(post(url)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .content(requestBody));
    }

}
