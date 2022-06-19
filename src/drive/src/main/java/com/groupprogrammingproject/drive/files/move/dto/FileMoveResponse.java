package com.groupprogrammingproject.drive.files.move.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class FileMoveResponse {
    private final String path;
}

