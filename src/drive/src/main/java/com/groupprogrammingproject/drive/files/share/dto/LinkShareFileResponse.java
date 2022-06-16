package com.groupprogrammingproject.drive.files.share.dto;

import com.groupprogrammingproject.drive.domain.file.FileAccessType;
import com.groupprogrammingproject.drive.domain.file.share.LinkFileShare;
import lombok.Builder;

@Builder
public record LinkShareFileResponse(
        String fileId,
        FileAccessType accessType,
        String linkId) {
    public static LinkShareFileResponse fromDomain(LinkFileShare linkFileShare) {
        return LinkShareFileResponse.builder()
                .fileId(linkFileShare.getFileId())
                .linkId(linkFileShare.getLinkId())
                .accessType(FileAccessType.valueOf(linkFileShare.getAccessType()))
                .build();
    }
}
