package com.groupprogrammingproject.drive.domain.file.share;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "LinkFileShare")
public class LinkFileShare {

    @Id
    private String fileId;

    private String linkId;

    private String accessType;


    @DynamoDBHashKey(attributeName = "fileId")
    public String getFileId() {
        return fileId;
    }

    @DynamoDBAttribute
    public String getAccessType() {
        return accessType;
    }

    @DynamoDBAttribute
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "linkId")
    public String getLinkId() {
        return linkId;
    }

}
