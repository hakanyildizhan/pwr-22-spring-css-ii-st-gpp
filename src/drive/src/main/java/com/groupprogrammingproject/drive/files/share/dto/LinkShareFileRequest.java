package com.groupprogrammingproject.drive.files.share.dto;

import com.groupprogrammingproject.drive.domain.file.FileAccessType;
import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record LinkShareFileRequest(
        @NotNull FileAccessType accessType
) {
}
