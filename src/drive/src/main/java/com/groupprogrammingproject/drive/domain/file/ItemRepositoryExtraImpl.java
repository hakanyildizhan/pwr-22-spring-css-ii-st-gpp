package com.groupprogrammingproject.drive.domain.file;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.groupprogrammingproject.drive.Utils;

@EnableScan
@Repository
public class ItemRepositoryExtraImpl implements ItemRepositoryExtra {

    private final ItemRepository itemRepository;

    public ItemRepositoryExtraImpl(@Lazy ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAllUnderPath(String path) {
        List<Item> allItemsUnderFolder = Utils.stream(itemRepository.findAll())
            .filter(i -> i.getPath().startsWith(path) && 
                i.getPath().replace(path, "").length() - i.getPath().replace(path, "").replace("/", "").length() == 1)
            .toList();

        return allItemsUnderFolder;
    }
}
