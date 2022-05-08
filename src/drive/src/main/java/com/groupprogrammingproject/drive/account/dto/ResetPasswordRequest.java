package com.groupprogrammingproject.drive.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.groupprogrammingproject.drive.exception.ExceptionMessage.CANNOT_BE_BLANK_MESSAGE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ResetPasswordRequest {

    @Length(min = 36, max = 36)
    // @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")
    @NotBlank(message = "Token" + CANNOT_BE_BLANK_MESSAGE)
    private final String token;

    @Length(min = 8)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-+_!@#$%^&*.,?]).+$")
    @NotBlank(message = "Password" + CANNOT_BE_BLANK_MESSAGE)
    private final String password;

}
