package com.groupprogrammingproject.drive.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "drive")
public class DriveProperties {

    private String secret;
    
}
