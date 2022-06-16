package com.groupprogrammingproject.drive.files.share.dto;

import com.groupprogrammingproject.drive.domain.file.FileAccessType;
import lombok.Builder;

import java.util.Map;

@Builder
public record PersonalShareFileRequest(
        Map<String, FileAccessType> personalAccessByEmail
) {
}
