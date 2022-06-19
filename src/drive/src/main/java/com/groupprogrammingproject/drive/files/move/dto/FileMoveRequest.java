package com.groupprogrammingproject.drive.files.move.dto;

import com.groupprogrammingproject.drive.domain.file.FileAccessType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class FileMoveRequest {
    @NotNull
    public final String itemId;

    @NotNull
    public final String newParentItemId;
}
