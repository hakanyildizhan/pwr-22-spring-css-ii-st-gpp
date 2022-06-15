package com.groupprogrammingproject.drive.files.list;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.groupprogrammingproject.drive.Utils;
import com.groupprogrammingproject.drive.domain.file.Item;
import com.groupprogrammingproject.drive.domain.file.ItemRepository;
import com.groupprogrammingproject.drive.domain.file.ItemType;
import com.groupprogrammingproject.drive.exception.NonexistentObjectException;
import com.groupprogrammingproject.drive.files.dto.FileItem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileListService {

    private final AmazonS3 amazonS3;

    private final ItemRepository itemRepository;

    @Value("${amazon.s3.bucket}")
    private String bucketName;

    public List<FileItem> getFilesAndFoldersUnderPath(String folderId, boolean root) {
        // get files
        String path = root ? folderId : itemRepository.findById(folderId).get().getPath();
        ObjectListing objects = amazonS3.listObjects(bucketName, path);
        List<S3ObjectSummary> objectSummaryList = objects.getObjectSummaries();
        objectSummaryList = Utils.stream(objectSummaryList).filter(o -> !o.getKey().replace(path + "/", "").contains("/")).toList();
        List<FileItem> items = new ArrayList<>();
        for (S3ObjectSummary obj : objectSummaryList) {
            String fileId = Utils.getFileKeyFromFullPath(obj.getKey());
            Item itemDB = itemRepository.findById(fileId).orElseThrow(NonexistentObjectException::new);

            if (itemDB != null) {
                FileItem item = new FileItem(itemDB.getName(), itemDB.getId().toString(), itemDB.getType());
                items.add(item);
            } else {
                log.error("No item found in bucket with key {}", obj.getKey());
            }
        }

        // get folders
        List<Item> folders = itemRepository.findAllUnderPath(Utils.getFileKeyFromFullPath(path));
        List<FileItem> foldersDto = Utils.stream(folders)
            .filter(f -> f.getType() == ItemType.FOLDER)
            .map(f -> new FileItem(f.getName(), f.getId(), f.getType())).toList();
        items.addAll(foldersDto);
        return items;
    }
}
