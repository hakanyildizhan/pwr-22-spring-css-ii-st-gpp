package com.groupprogrammingproject.drive.domain.file.share;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "PersonalFileShare")
public class PersonalFileShare {

    @Id
    private String fileId;

    private String personalAccess;


    @DynamoDBHashKey(attributeName = "fileId")
    public String getFileId() {
        return fileId;
    }

    @DynamoDBAttribute
    public String getPersonalAccess() {
        return personalAccess;
    }

    public void setFileId(String id) {
        this.fileId = id;
    }

    public void setPersonalAccess(String personalAccess) {
        this.personalAccess = personalAccess;
    }
}
