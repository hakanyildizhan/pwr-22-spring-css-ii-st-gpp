package com.groupprogrammingproject.drive.files.share;

import com.groupprogrammingproject.drive.domain.file.share.LinkFileShare;
import com.groupprogrammingproject.drive.files.share.dto.LinkShareFileRequest;
import com.groupprogrammingproject.drive.files.share.dto.LinkShareFileResponse;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@AllArgsConstructor
public class ShareFileController {

    private final ShareFileService shareFileService;

    @PutMapping("/files/{fileId}/link-access")
    public LinkShareFileResponse shareFileByLink(@RequestBody @Valid LinkShareFileRequest request, @PathVariable("fileId") String fileId) {
        LinkFileShare linkFileShare = shareFileService.shareFile(fileId, request.accessType());
        return LinkShareFileResponse.fromDomain(linkFileShare);
    }
}
