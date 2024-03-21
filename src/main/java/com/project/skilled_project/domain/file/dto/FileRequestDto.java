package com.project.skilled_project.domain.file.dto;

import jakarta.persistence.Column;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileRequestDto {

  private Long sourceId;

  private String category;

  private List<MultipartFile> files;
}
