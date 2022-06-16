package com.groupprogrammingproject.drive.files.share;

import com.groupprogrammingproject.drive.domain.file.FileAccessType;
import com.groupprogrammingproject.drive.domain.file.share.LinkFileShare;
import com.groupprogrammingproject.drive.domain.file.share.LinkFileShareRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.UUID.randomUUID;

@Slf4j
@Service
@AllArgsConstructor
public class ShareFileService {

    private final LinkFileShareRepository linkFileShareRepository;

    public LinkFileShare shareFile(String fileId, FileAccessType accessType) {
        LinkFileShare linkFileShare = new LinkFileShare(fileId, randomUUID().toString(), accessType.name());
        return linkFileShareRepository.save(linkFileShare);
    }
}
