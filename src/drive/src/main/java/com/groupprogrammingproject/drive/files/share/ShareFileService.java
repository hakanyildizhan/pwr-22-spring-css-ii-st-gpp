package com.groupprogrammingproject.drive.files.share;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupprogrammingproject.drive.domain.file.FileAccessType;
import com.groupprogrammingproject.drive.domain.file.share.LinkFileShare;
import com.groupprogrammingproject.drive.domain.file.share.LinkFileShareRepository;
import com.groupprogrammingproject.drive.domain.file.share.PersonalFileShare;
import com.groupprogrammingproject.drive.domain.file.share.PersonalFileShareRepository;
import com.groupprogrammingproject.drive.domain.security.AuthorizationData;
import com.groupprogrammingproject.drive.domain.security.AuthorizationDataRepository;
import com.groupprogrammingproject.drive.exception.AccountWithGivenEmailAlreadyExists;
import com.groupprogrammingproject.drive.exception.NonexistentAccountException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@Slf4j
@Service
@AllArgsConstructor
public class ShareFileService {

    private final LinkFileShareRepository linkFileShareRepository;

    private final PersonalFileShareRepository personalFileShareRepository;

    private final AuthorizationDataRepository authorizationDataRepository;

    private final ObjectMapper objectMapper;

    public LinkFileShare shareFile(String fileId, FileAccessType accessType) {
        LinkFileShare linkFileShare = new LinkFileShare(fileId, randomUUID().toString(), accessType.name());
        return linkFileShareRepository.save(linkFileShare);
    }

    public PersonalFileShare shareFileWithPeople(String fileId, Map<String, FileAccessType> personalAccessByEmail) throws JsonProcessingException {
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
