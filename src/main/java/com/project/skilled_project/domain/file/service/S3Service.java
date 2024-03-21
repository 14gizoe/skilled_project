package com.project.skilled_project.domain.file.service;

import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

  private final S3Client s3Client;

  @Value("${card.path}")
  private String cardPath;

  @Value("${profile.path}")
  private String profilePath;

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  public String uploadFile(MultipartFile file, String category) {
    String fileName = generateFileName(file.getOriginalFilename());
    String filePath = determineFilePath(category, fileName);

    try {
      PutObjectRequest putObjectRequest = PutObjectRequest.builder()
          .bucket(bucketName)
          .key(filePath)
          .contentType(StringUtils.getFilenameExtension(file.getOriginalFilename()))
          .build();
      s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
      return s3Client.utilities().getUrl(
          GetUrlRequest.builder().bucket(bucketName)
              .key(filePath).build()
      ).toExternalForm();
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("파일 업로드에 실패했습니다.");
    }
  }

  private String generateFileName(String originalFileName) {
    String uuid = UUID.randomUUID().toString();
    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
    return uuid + extension;
  }

  private String determineFilePath(String category, String fileName) {
    if ("profile".equals(category)) {
      return profilePath + fileName;
    } else if ("card".equals(category)) {
      return cardPath + fileName;
    }
    throw new IllegalArgumentException("잘못된 카테고리입니다.");
  }
}
