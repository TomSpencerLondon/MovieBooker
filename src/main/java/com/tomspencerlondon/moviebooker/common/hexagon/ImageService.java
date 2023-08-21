package com.tomspencerlondon.moviebooker.common.hexagon;

import com.tomspencerlondon.moviebooker.config.AWSS3Config;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
  private final AWSS3Config awsS3Config;

  public ImageService(AWSS3Config awsS3Config) {
    this.awsS3Config = awsS3Config;
  }

  public String imagePath(String image) {
    String s3EndpointUrl = awsS3Config.getS3EndpointUrl();
    String bucketName = awsS3Config.getBucketName();
    return "%s/%s/%s".formatted(s3EndpointUrl, bucketName, image);
  }
}
