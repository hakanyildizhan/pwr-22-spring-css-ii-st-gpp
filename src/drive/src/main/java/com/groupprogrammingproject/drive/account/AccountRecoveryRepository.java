package com.groupprogrammingproject.drive.account;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@EnableScan
@Repository
public interface AccountRecoveryRepository extends CrudRepository<AccountRecoveryData, UUID> {
    Optional<AccountRecoveryData> findByToken(UUID token);
}
