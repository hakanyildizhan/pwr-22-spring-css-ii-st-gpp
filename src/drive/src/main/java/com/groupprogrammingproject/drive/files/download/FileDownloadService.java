package com.groupprogrammingproject.drive.files.download;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.groupprogrammingproject.drive.domain.file.Item;
import com.groupprogrammingproject.drive.domain.file.ItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileDownloadService {

    private final AmazonS3 amazonS3;
    private final ItemRepository itemRepository;

    @Value("${amazon.s3.bucket}")
    private String bucketName;

    public Resource downloadFile(String key) throws IOException {
        Item item = itemRepository.findById(key).orElse(null);
        if (item == null) {
            log.error("Item with key {} was not found in the repository", key);
            return null;
        }
        String path = item.getPath();

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
