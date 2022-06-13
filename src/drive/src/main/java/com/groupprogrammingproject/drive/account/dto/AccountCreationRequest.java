package com.groupprogrammingproject.drive.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.groupprogrammingproject.drive.exception.ExceptionMessage.CANNOT_BE_BLANK_MESSAGE;
import static com.groupprogrammingproject.drive.exception.ExceptionMessage.MUST_MATCH_EMAIL_PATTERN_MESSAGE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AccountCreationRequest {

    @Email(message = MUST_MATCH_EMAIL_PATTERN_MESSAGE)
    @NotBlank(message = "Email" + CANNOT_BE_BLANK_MESSAGE)
    private final String email;

    @Length(min = 8)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-+_!@#$%^&*.,?]).+$")
    @NotBlank(message = "Password" + CANNOT_BE_BLANK_MESSAGE)
    private final String password;

}
