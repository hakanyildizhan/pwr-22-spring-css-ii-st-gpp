package com.groupprogrammingproject.drive.files.upload;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.groupprogrammingproject.drive.domain.file.Item;
import com.groupprogrammingproject.drive.domain.file.ItemRepository;
import com.groupprogrammingproject.drive.domain.file.ItemType;
import com.groupprogrammingproject.drive.domain.security.AuthorizationDataRepository;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    public static final String FILES_ENDPOINT = "/files";

    private final FileUploadService fileUploadService;
    private final ItemRepository itemRepository;
    //private final AuthorizationDataRepository accountRepository;

    @PostMapping(FILES_ENDPOINT)
    public String uploadFile(@RequestPart(value = "file") MultipartFile file, @RequestPart(value = "parentFolder") String parentFolder) {
        try {
        	String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            String fileKey = UUID.randomUUID().toString();
            String parentPath = parentFolder.isBlank() 
            || parentFolder == null 
            || parentFolder.equals(".")
            || parentFolder.equals("./")
            ? "" : parentFolder + '/';
            String filePath = '/' + userId + '/' + parentPath + fileKey;

            Item item = new Item();
            item.setName(file.getName());
            item.setId(fileKey);
            item.setPath(filePath);
            item.setType(ItemType.FILE);
            itemRepository.save(item);

            return this.fileUploadService.uploadFile(file, filePath);
        } catch (Exception e) {
            return "No";
        }
    }
}
