package com.groupprogrammingproject.drive.domain.file;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ItemShareUser")
public class ItemShareUser {
    private String itemId;
    private String ownerId;

    public ItemShareUser(String itemId, String ownerId) {
        this.itemId = itemId;
        this.ownerId = ownerId;
    }

    public ItemShareUser() {
    }

    @DynamoDBHashKey(attributeName="ownerId")
    public String getOwnerId() {
        return ownerId;
    }

    @DynamoDBAttribute
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "itemId")
    public String getItemId() {
        return itemId;
    }
}
