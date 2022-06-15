package com.groupprogrammingproject.drive.domain.file;

import java.util.List;

public interface ItemRepositoryExtra{

    List<Item> findAllUnderPath(String folderId);

}
