package com.groupprogrammingproject.drive.files.share.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupprogrammingproject.drive.domain.file.FileAccessType;
import com.groupprogrammingproject.drive.domain.file.share.PersonalFileShare;
import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder
public record PersonalShareFileResponse(
        String fileId,
        Map<UUID, FileAccessType> personalAccess) {
    public static PersonalShareFileResponse fromDomain(PersonalFileShare personalFileShare, ObjectMapper objectMapper) throws JsonProcessingException {
        TypeReference<Map<UUID, FileAccessType>> typeRef
                = new TypeReference<Map<UUID, FileAccessType>>() {
        };
        return PersonalShareFileResponse.builder()
                .fileId(personalFileShare.getFileId())
                .personalAccess(objectMapper.readValue(personalFileShare.getPersonalAccess(), typeRef))
                .build();
    }
}
