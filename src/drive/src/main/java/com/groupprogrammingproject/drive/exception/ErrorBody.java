package com.groupprogrammingproject.drive.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorBody {

    private final String message;

    private final String originalMessage;

    private final String code;
}
