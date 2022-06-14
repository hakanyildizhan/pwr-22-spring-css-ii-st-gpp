package com.groupprogrammingproject.drive.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.groupprogrammingproject.drive.domain.file.ItemRepository;
import com.groupprogrammingproject.drive.domain.security.AuthorizationDataRepository;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import static com.amazonaws.regions.Regions.EU_WEST_1;

@Configuration
@EnableDynamoDBRepositories(basePackageClasses = { AuthorizationDataRepository.class, ItemRepository.class })
public class AWSServicesConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.s3.endpoint}")
    private String amazonS3Endpoint;

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    @Bean
    public AmazonS3 amazonS3Client() {
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(amazonAWSCredentials()))
                .build();

        if (!ObjectUtils.isEmpty(amazonDynamoDBEndpoint)) {
            amazonS3 = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(amazonAWSCredentials()))
                    .withEndpointConfiguration(new EndpointConfiguration(amazonS3Endpoint, EU_WEST_1.getName()))
                    .build();
        }
        return amazonS3;
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(amazonAWSCredentials()))
                .build();

        if (!ObjectUtils.isEmpty(amazonDynamoDBEndpoint)) {
            amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(amazonAWSCredentials()))
                    .withEndpointConfiguration(new EndpointConfiguration(amazonDynamoDBEndpoint, EU_WEST_1.getName()))
                    .build();
        }

        return amazonDynamoDB;
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(
                amazonAWSAccessKey, amazonAWSSecretKey);
    }
}