package com.groupprogrammingproject.drive.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.groupprogrammingproject.drive.exception.ExceptionMessage.CANNOT_BE_BLANK_MESSAGE;
import static com.groupprogrammingproject.drive.exception.ExceptionMessage.MUST_MATCH_EMAIL_PATTERN_MESSAGE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ForgotPasswordRequest {

    @Email(message = MUST_MATCH_EMAIL_PATTERN_MESSAGE)
    @NotBlank(message = "Email" + CANNOT_BE_BLANK_MESSAGE)
    private final String email;

}
