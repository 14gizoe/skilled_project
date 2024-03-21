package com.project.skilled_project.domain.file.dto;

import lombok.Getter;

@Getter
public class FileResponseDto {

  private Long sourceId;

  private String category;

  private String originalFileName;

  private String filePath;

  public FileResponseDto(Long sourceId, String category, String orifinalFileName, String filePath) {
    this.sourceId = sourceId;
    this.category = category;
    this.originalFileName = orifinalFileName;
    this.filePath = filePath;
  }
}
