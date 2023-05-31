package com.tomspencerlondon.moviebooker.admin.hexagon.application;

import com.tomspencerlondon.moviebooker.admin.hexagon.application.port.FileRepository;
import com.tomspencerlondon.moviebooker.admin.hexagon.domain.File;
import com.tomspencerlondon.moviebooker.config.AWSS3Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
public class AdminImageUploadService {
    private final S3Client s3Client;
    private final AWSS3Config awsS3Config;
    private final FileRepository fileRepository;


    @Autowired
    public AdminImageUploadService(S3Client s3Client, AWSS3Config awsS3Config, FileRepository fileRepository) {
        this.s3Client = s3Client;
        this.awsS3Config = awsS3Config;
        this.fileRepository = fileRepository;
    }

    public File uploadObjectToS3(String fileName, InputStream inputStream) throws IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(awsS3Config.getBucketName())
                .key(fileName)
                .build();

        PutObjectResponse putObjectResponse = s3Client.putObject(
                request,
                RequestBody.fromInputStream(inputStream, inputStream.available()));

        String fileUrl = awsS3Config.getS3EndpointUrl() + "/" + awsS3Config.getBucketName() + "/" + fileName;

        File file = new File(null, fileName, fileUrl, Objects.nonNull(putObjectResponse));

        return fileRepository.save(file);
    }

}
