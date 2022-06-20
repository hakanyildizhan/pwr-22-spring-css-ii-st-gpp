package com.groupprogrammingproject.drive.domain.security;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Date;
import java.util.UUID;

@DynamoDBTable(tableName = "RecoveryTokens")
public class RecoveryToken {
    private String id;
    private String email;
    private Date created;
    private Date expired;
    private Integer status;

    public RecoveryToken() { }

    public RecoveryToken(String email) {
        final Date today = new Date();

        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.created = today;
        this.expired = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        this.status = RecoveryTokenStatus.ACTIVE.ordinal();
    }

    public RecoveryToken(String id, String email, Date created, Date expired, Integer status) {
        this.id = id;
        this.email = email;
        this.created = created;
        this.expired = expired;
        this.status = status;
    }

    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }
    public void setId(String id) { this.id = id; }

    @DynamoDBAttribute
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "email")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) { this.email = email; }

    @DynamoDBAttribute
    public Integer getStatus() {
        return status;
    }
    public void setStatus(RecoveryTokenStatus status) {
        this.status = status.ordinal();
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    @DynamoDBAttribute
    public Date getCreated() { return created; }
    public void setCreated(Date created) { this.created = created; }

    @DynamoDBAttribute
    public Date getExpired() { return expired; }
    public void setExpired(Date expired) { this.expired = expired; }
}
