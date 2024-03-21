package com.project.skilled_project.domain.checklist.dto.response;


import lombok.Getter;

@Getter
public class ChecklistResponseDto {
  private Long checklistId;
  private String content;
  private Boolean completed;

  public ChecklistResponseDto(Long checklistId, String content, Boolean completed) {
    this.checklistId = checklistId;
    this.content = content;
    this.completed = completed;
  }
}
