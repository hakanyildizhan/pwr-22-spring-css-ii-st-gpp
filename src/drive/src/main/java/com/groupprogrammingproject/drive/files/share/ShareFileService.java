package com.groupprogrammingproject.drive.files.share;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupprogrammingproject.drive.domain.file.FileAccessType;
import com.groupprogrammingproject.drive.domain.file.Item;
import com.groupprogrammingproject.drive.domain.file.ItemRepository;
import com.groupprogrammingproject.drive.domain.file.share.LinkFileShare;
import com.groupprogrammingproject.drive.domain.file.share.LinkFileShareRepository;
import com.groupprogrammingproject.drive.domain.file.share.PersonalFileShare;
import com.groupprogrammingproject.drive.domain.file.share.PersonalFileShareRepository;
import com.groupprogrammingproject.drive.domain.security.AuthorizationData;
import com.groupprogrammingproject.drive.domain.security.AuthorizationDataRepository;
import com.groupprogrammingproject.drive.exception.NonexistentAccountException;
import com.groupprogrammingproject.drive.exception.NonexistentObjectException;
import com.groupprogrammingproject.drive.exception.UnauthorizedFileAccessException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@Service
@AllArgsConstructor
public class ShareFileService {

    private final LinkFileShareRepository linkFileShareRepository;

    private final PersonalFileShareRepository personalFileShareRepository;

    private final AuthorizationDataRepository authorizationDataRepository;

    private final ItemRepository itemRepository;

    private final ObjectMapper objectMapper;

    public LinkFileShare shareFile(String fileId, FileAccessType accessType) {
        String ownerId = SecurityContextHolder.getContext().getAuthentication().getName();
        String path = itemRepository.findById(fileId)
                .map(Item::getPath)
                .orElseThrow(NonexistentObjectException::new);
        if (!path.startsWith(ownerId)) {
            throw new UnauthorizedFileAccessException();
        }
        LinkFileShare linkFileShare = new LinkFileShare(fileId, randomUUID().toString(), accessType.name());
        return linkFileShareRepository.save(linkFileShare);
    }

    public PersonalFileShare shareFileWithPeople(String fileId, Map<String, FileAccessType> personalAccessByEmail) throws JsonProcessingException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        String path = itemRepository.findById(fileId)
                .map(Item::getPath)
                .orElseThrow(NonexistentObjectException::new);
        if (!path.startsWith(userId)) {
            throw new UnauthorizedFileAccessException();
        }
        Map<UUID, FileAccessType> personalAccess = personalAccessByEmail.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> findUserId(entry.getKey()), Map.Entry::getValue));
        PersonalFileShare personalFileShare = new PersonalFileShare(fileId, objectMapper.writeValueAsString(personalAccess));
        return personalFileShareRepository.save(personalFileShare);
    }

    private UUID findUserId(String email) {
        return UUID.fromString(authorizationDataRepository
                .findByEmail(email)
                .map(AuthorizationData::getId)
                .orElseThrow(NonexistentAccountException::new));
    }
}
