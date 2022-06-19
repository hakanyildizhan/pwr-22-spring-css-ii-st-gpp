package com.groupprogrammingproject.drive.files.move;

import com.amazonaws.services.s3.AmazonS3;
import com.groupprogrammingproject.drive.Utils;
import com.groupprogrammingproject.drive.domain.file.Item;
import com.groupprogrammingproject.drive.domain.file.ItemRepository;
import com.groupprogrammingproject.drive.domain.file.ItemType;
import com.groupprogrammingproject.drive.exception.NonexistentObjectException;
import com.groupprogrammingproject.drive.files.move.dto.FileMoveRequest;
import com.groupprogrammingproject.drive.files.move.dto.FileMoveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileMoveService {

    private final AmazonS3 amazonS3;

    private final ItemRepository itemRepository;

    @Value("${amazon.s3.bucket}")
    private String bucketName;

    public FileMoveResponse move(FileMoveRequest fileMoveRequest, String userId) {
        try {
            Item item = itemRepository.findById(fileMoveRequest.getItemId()).get();
            Item folder = itemRepository.findById(fileMoveRequest.getNewParentItemId()).get();
            String parentPath = getFolderFullPathByFolderId(fileMoveRequest.getNewParentItemId());

            // check if folder already exists
            Item existingFolder = Utils.stream(itemRepository.findAll())
                    .filter(i -> i.getPath().equals(parentPath))
                    .findFirst()
                    .orElse(null);

            if (existingFolder == null || existingFolder.getType() != ItemType.FOLDER) throw new Exception();
            if (Utils.isRootPath(item.getPath())) throw new Exception("Cannot move root folder");
            if (Utils.isRootPath(folder.getPath())) folder.setPath(userId);

            Item movedItem = switch (item.getType()) {
                case ItemType.FOLDER -> moveFolder(item, folder);
                case ItemType.FILE -> moveFile(item, folder);
                default -> null;
            };

            return new FileMoveResponse(movedItem.getPath());
        } catch (Exception e) {
            throw new NonexistentObjectException();
        }
    }

    private Item moveFile(Item file, Item folder) {
        amazonS3.copyObject(bucketName, file.getPath(), bucketName, folder.getPath());
        amazonS3.deleteObject(bucketName, file.getPath());
        file.setPath(folder.getPath());
        return itemRepository.save(file);
    }

    @Transactional
    private Item moveFolder(Item folder, Item newFolder) {
        List<Item> allFiles = itemRepository.findAllUnderPath(Utils.getFileKeyFromFullPath(folder.getPath()));
        List<Item> folders = allFiles.stream().filter(item -> item.getType() == ItemType.FOLDER).toList(); // .sorted(Comparator.comparingInt(s -> s.getPath().length()))
        List<Item> files = allFiles.stream().filter(item -> item.getType() == ItemType.FILE).toList();
        for (Item f : folders) {
            f.setPath(f.getPath().replaceFirst(folder.getPath(), newFolder.getPath()));
            itemRepository.save(f);
            // this.moveFolder(f, newFolder);
        }
        for (Item f : files) {
            f.setPath(f.getPath().replaceFirst(folder.getPath(), newFolder.getPath()));
            itemRepository.save(f);
            // this.moveFile(f, newFolder);
        }

        amazonS3.copyObject(bucketName, folder.getPath(), bucketName, newFolder.getPath());
        amazonS3.deleteObject(bucketName, folder.getPath());
        folder.setPath(newFolder.getPath());

        return itemRepository.save(folder);
    }

    private String getFolderFullPathByFolderId(String parentFolder) {
        if (Utils.isRootPath(parentFolder)) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } else {
            Item folder = itemRepository.findById(parentFolder).get();
            return folder.getPath();
        }
    }
}
