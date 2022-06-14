package com.groupprogrammingproject.drive.domain.file;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Item")
public class Item {

    @Id
    private String id;
    private Integer type;
    private String path;
    private String name;

    public Item(Integer type, String id, String path, String name) {
        this.id = id;
        this.type = type;
        this.path = path;
        this.name = name;
    }

    public Item() {
    }

    @DynamoDBHashKey(attributeName="id")
    public String getId() {
        return id;
    }

    @DynamoDBAttribute
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "path")
    public String getPath() {
        return path;
    }

    @DynamoDBAttribute
    public String getName() {
        return name;
    }

    @DynamoDBAttribute
    public Integer getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
