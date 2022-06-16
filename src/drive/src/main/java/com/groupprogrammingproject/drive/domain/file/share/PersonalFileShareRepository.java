package com.groupprogrammingproject.drive.domain.file.share;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalFileShareRepository extends CrudRepository<PersonalFileShare, String> {
}
