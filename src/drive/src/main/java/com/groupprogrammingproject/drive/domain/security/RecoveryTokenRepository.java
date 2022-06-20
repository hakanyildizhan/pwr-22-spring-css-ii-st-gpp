package com.groupprogrammingproject.drive.domain.security;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableScan
@Repository
public interface RecoveryTokenRepository extends CrudRepository<RecoveryToken, String> {
    Optional<RecoveryToken> findById(String id);
    Optional<List<RecoveryToken>> findAllByEmail(String email);
}
