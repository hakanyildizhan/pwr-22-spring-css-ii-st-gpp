package com.groupprogrammingproject.drive.files.download;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.groupprogrammingproject.drive.domain.file.Item;
import com.groupprogrammingproject.drive.domain.file.ItemRepository;

import com.groupprogrammingproject.drive.domain.file.share.PersonalFileShare;
import com.groupprogrammingproject.drive.domain.file.share.PersonalFileShareRepository;
import com.groupprogrammingproject.drive.exception.NonexistentObjectException;
import com.groupprogrammingproject.drive.exception.UnauthorizedFileAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileDownloadService {

    private final AmazonS3 amazonS3;
    private final ItemRepository itemRepository;

    private final PersonalFileShareRepository personalFileShareRepository;

    @Value("${amazon.s3.bucket}")
    private String bucketName;

    public Resource downloadFile(String key) throws IOException {
        Item item = itemRepository.findById(key).orElse(null);
        if (item == null) {
            log.error("Item with key {} was not found in the repository", key);
            return null;
        }
        String path = item.getPath();
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        PersonalFileShare personalFileShare = personalFileShareRepository.findById(key)
                .orElse(null);
        if (!path.startsWith(userId) || !(personalFileShare != null && personalFileShare.getPersonalAccess().contains(userId))) {
            throw new UnauthorizedFileAccessException();
        }
        try {
            return new ByteArrayResource(amazonS3.getObject(bucketName, path).getObjectContent().readAllBytes());
        } catch (AmazonServiceException e) {
            log.error("S3 Object with key path {} was not found in the bucket: {}", path, e.getErrorMessage());
            return null;
        } catch (IOException e) {
            log.error("Error while reading bytes from S3 Object with key path {}: {}", path, e.getMessage());
            return null;
        }
    }
}
