package com.groupprogrammingproject.drive.files.upload;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.groupprogrammingproject.drive.Utils;
import com.groupprogrammingproject.drive.domain.file.Item;
import com.groupprogrammingproject.drive.domain.file.ItemRepository;
import com.groupprogrammingproject.drive.domain.file.ItemType;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    public static final String FILES_ENDPOINT = "/files";

    private final FileUploadService fileUploadService;
    private final ItemRepository itemRepository;

    @PostMapping(FILES_ENDPOINT + "/upload")
    public ResponseEntity<String> uploadFile(@RequestPart(value = "file") MultipartFile file, @RequestParam(value = "parentFolder") String parentFolder) {
        try {
            String fileKey = UUID.randomUUID().toString();
            String fileFriendlyName = file.getOriginalFilename();
            String parentPath = getFolderFullPathByFolderId(parentFolder);
            String filePath = Utils.createFilePath(parentPath, fileKey);

            Item item = new Item();
            item.setName(fileFriendlyName);
            item.setId(fileKey);
            item.setPath(filePath);
            item.setType(ItemType.FILE);
            itemRepository.save(item);

            if (this.fileUploadService.uploadFile(file, filePath)) {
                return ResponseEntity.ok().body(fileKey);
            } else {
                itemRepository.delete(itemRepository.findById(fileKey).get());
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(FILES_ENDPOINT + "/createFolder")
    public ResponseEntity<String> createFolder(@RequestParam(value = "folderName") String folderName, @RequestParam(value = "parentFolder") String parentFolder) {
        String parentPath = getFolderFullPathByFolderId(parentFolder);
        String folderKey = UUID.randomUUID().toString();
        String newFolderFullPath = Utils.createFilePath(parentPath, folderKey);

        // check if folder already exists
        Item existingFolder = Utils.stream(itemRepository.findAll())
            .filter(i -> i.getPath().equals(newFolderFullPath))
            .findFirst()
            .orElse(null);

        if (existingFolder != null) {
            return ResponseEntity.badRequest().build();
        }

        Item item = new Item();
        item.setName(folderName);
        item.setId(folderKey);
        item.setPath(newFolderFullPath);
        item.setType(ItemType.FOLDER);
        itemRepository.save(item);
        return ResponseEntity.ok().body(folderKey);
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
