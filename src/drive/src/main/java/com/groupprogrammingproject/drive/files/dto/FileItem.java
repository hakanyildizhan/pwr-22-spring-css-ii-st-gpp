package com.groupprogrammingproject.drive.files.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class FileItem {
    public final String displayName;
    public final String id;
    public final Integer type;
}
