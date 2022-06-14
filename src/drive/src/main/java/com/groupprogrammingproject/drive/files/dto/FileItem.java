package com.groupprogrammingproject.drive.files.dto;

import com.groupprogrammingproject.drive.domain.file.ItemType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class FileItem {
    private final String displayName;
    private final String id;
    private final Integer type;
}
