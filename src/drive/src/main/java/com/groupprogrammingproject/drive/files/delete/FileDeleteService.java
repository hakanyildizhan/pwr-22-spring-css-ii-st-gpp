package com.groupprogrammingproject.drive.files.delete;


import com.amazonaws.services.s3.AmazonS3;
import com.groupprogrammingproject.drive.domain.file.Item;
import com.groupprogrammingproject.drive.domain.file.ItemRepository;
import com.groupprogrammingproject.drive.exception.NonexistentObjectException;
import com.groupprogrammingproject.drive.exception.UnauthorizedFileAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileDeleteService {

    private final AmazonS3 amazonS3;

    private final ItemRepository itemRepository;

    @Value("${amazon.s3.bucket}")
    private String bucketName;

    public void deleteFile(String key) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        String path = itemRepository.findById(key)
                .map(Item::getPath)
                .orElseThrow(NonexistentObjectException::new);
        if (!path.startsWith(userId)) {
            throw new UnauthorizedFileAccessException();
        }
        try {
            itemRepository.deleteById(key);
            amazonS3.deleteObject(bucketName, key);
        } catch (Exception e) {
            log.error("Error while deleting item: {}", e.getMessage());
        }
    }
}
