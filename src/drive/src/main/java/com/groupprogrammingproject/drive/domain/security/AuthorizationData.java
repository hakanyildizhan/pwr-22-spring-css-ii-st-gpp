package com.groupprogrammingproject.drive.domain.security;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Users")
public class AuthorizationData {

    private String id;
    private String email;
    private String password;
    private String role;
    private String status;

    public AuthorizationData(String id, String email, String password, String role, String status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public AuthorizationData() {
    }

    @DynamoDBHashKey(attributeName="id")
    public String getId() {
        return id;
    }

    @DynamoDBAttribute
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "email")
    public String getEmail() {
        return email;
    }

    @DynamoDBAttribute
    public String getPassword() {
        return password;
    }

    @DynamoDBAttribute
    public String getRole() {
        return role;
    }

    @DynamoDBAttribute
    public String getStatus() {
        return status;
    }
}
