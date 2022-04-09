package com.groupprogrammingproject.drive.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {

    private final String token;

    private final String userId;

    private int expiresIn;

}
