package com.groupprogrammingproject.drive.domain.security;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@EnableScan
@Repository
public interface AuthorizationDataRepository extends CrudRepository<AuthorizationData, UUID> {

    Optional<AuthorizationData> findById(UUID id);

    Optional<AuthorizationData> findByEmail(String email);

}
