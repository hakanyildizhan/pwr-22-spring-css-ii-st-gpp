package com.groupprogrammingproject.drive.files.list;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.groupprogrammingproject.drive.domain.file.Item;
import com.groupprogrammingproject.drive.domain.file.ItemRepository;
import com.groupprogrammingproject.drive.exception.NonexistentObjectException;
import com.groupprogrammingproject.drive.files.dto.FileItem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileListService {

    private final AmazonS3 amazonS3;

    private final ItemRepository itemRepository;

    @Value("${amazon.s3.bucket}")
    private String bucketName;

    public List<FileItem> getFilesUnderPath(String path) {
        ObjectListing objects = amazonS3.listObjects(bucketName, path);
        List<S3ObjectSummary> objectSummaryList = objects.getObjectSummaries();
        List<FileItem> items = new ArrayList<>();
        for (S3ObjectSummary obj : objectSummaryList) {
            Item itemDB = itemRepository.findById(obj.getKey()).orElseThrow(NonexistentObjectException::new);

            if (itemDB != null) {
                FileItem item = new FileItem(itemDB.getName(), itemDB.getId().toString(), itemDB.getType());
                items.add(item);
            } else {
                log.error("No item found in bucket with key {0}", obj.getKey());
            }
            
        }
        return items;
    }
}
