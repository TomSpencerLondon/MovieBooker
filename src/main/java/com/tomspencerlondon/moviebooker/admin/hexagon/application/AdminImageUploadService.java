package com.tomspencerlondon.moviebooker.admin.hexagon.application;

import com.tomspencerlondon.moviebooker.config.AWSS3Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Service
public class AdminImageUploadService {
    private final S3Client s3Client;
    private final AWSS3Config awsS3Config;

    @Autowired
    public AdminImageUploadService(S3Client s3Client, AWSS3Config awsS3Config) {
        this.s3Client = s3Client;
        this.awsS3Config = awsS3Config;
    }

    public void uploadObjectToS3(String fileName, InputStream inputStream) throws IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(awsS3Config.getBucketName())
                .key(fileName)
                .build();

        s3Client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
    }

}
