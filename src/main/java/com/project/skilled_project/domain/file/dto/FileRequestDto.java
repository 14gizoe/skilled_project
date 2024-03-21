package com.project.skilled_project.domain.file.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileRequestDto {

  private Long sourceId;

  private String category;
}
