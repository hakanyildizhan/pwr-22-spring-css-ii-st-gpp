package com.groupprogrammingproject.drive.domain.file;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ItemShareLink")
public class ItemShareLink {
    private String itemId;
    private String shareId;

    public ItemShareLink(String itemId, String shareId) {
        this.itemId = itemId;
        this.shareId = shareId;
    }

    public ItemShareLink() {
    }

    @DynamoDBHashKey(attributeName="shareId")
    public String geShareId() {
        return shareId;
    }

    @DynamoDBAttribute
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "itemId")
    public String getItemId() {
        return itemId;
    }
}
