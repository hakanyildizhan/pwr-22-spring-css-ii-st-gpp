package com.groupprogrammingproject.drive.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class AccountCreationResponse {

    private final UUID userId;

}
