package com.tomspencerlondon.moviebooker.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.credentials.internal.StaticResourcesEndpointProvider;
import software.amazon.awssdk.services.apigateway.model.EndpointConfiguration;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.net.URISyntaxException;

@Data
@Configuration
public class AWSS3Config {

    @Value("${config.aws.region}")          private String region;
    @Value("${config.aws.s3.url}")          private String s3EndpointUrl;
    @Value("${config.aws.s3.bucket-name}")  private String bucketName;
    @Value("${config.aws.s3.access-key}")   private String accessKey;
    @Value("${config.aws.s3.secret-key}")   private String secretKey;

    @Bean(name = "amazonS3")
    public S3Client amazonS3() throws URISyntaxException {
        return S3Client.builder()
                .credentialsProvider(getCredentialsProvider())
                .endpointOverride(new URI(s3EndpointUrl))
                .build();
    }

    private AwsCredentialsProvider getCredentialsProvider() {
        return StaticCredentialsProvider.create(getBasicAWSCredentials());
    }

    private AwsBasicCredentials getBasicAWSCredentials() {
        return AwsBasicCredentials.create(accessKey, secretKey);
    }
}
