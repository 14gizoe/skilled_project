package com.project.skilled_project.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

  @Value("${cloud.aws.s3.credentials.accessKey}")
  private String accessKey;

  @Value("${cloud.aws.s3.credentials.secretKey}")
  private String secretKey;

  @Value("${cloud.aws.region.static}")
  private String region;

  @Bean
  @Primary
  public AwsBasicCredentials awsCredentialsProvider() {
    AwsBasicCredentials basicAWSCredentials = AwsBasicCredentials.create(accessKey, secretKey);
    return basicAWSCredentials;
  }

  @Bean
  public S3Client amazonS3() {
    return S3Client.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(awsCredentialsProvider()))
        .build();
  }

  @Bean
  public S3Presigner presigner() {
    return S3Presigner.builder()
        .credentialsProvider(StaticCredentialsProvider.create(awsCredentialsProvider()))
        .region(Region.of(region))
        .build();
  }
}
