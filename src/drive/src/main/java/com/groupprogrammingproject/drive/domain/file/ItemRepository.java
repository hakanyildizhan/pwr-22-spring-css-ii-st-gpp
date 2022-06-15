package com.groupprogrammingproject.drive.domain.file;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableScan
@Repository
public interface ItemRepository extends CrudRepository<Item, String>, ItemRepositoryExtra {

    Optional<Item> findById(String id);
}