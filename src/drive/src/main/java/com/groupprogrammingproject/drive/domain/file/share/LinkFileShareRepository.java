package com.groupprogrammingproject.drive.domain.file.share;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkFileShareRepository extends CrudRepository<LinkFileShare, String> {

    LinkFileShare findByLinkId(String linkId);
}
