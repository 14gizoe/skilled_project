package com.project.skilled_project.domain.file.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "file")
public class File {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "source_id")
  private Long sourceId;

  @Column
  private String category;

//  @Column(name = "save_file_name")
//  private String saveFileName;

  @Column(name = "original_file_name")
  private String originalFileName;

  @Column(name = "file_path")
  private String filePath;

  public File(Long sourceId, String category, String originalFileName, String filePath) {
    this.sourceId = sourceId;
    this.category = category;
    this.originalFileName = originalFileName;
    this.filePath = filePath;
  }
}
