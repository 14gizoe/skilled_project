package com.project.skilled_project.domain.card.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CardResponseDto {
  private Long cardId;
  private String title;
  private String color;
  private Long workerCount;
  private Long fileCount;
  private Long commentCount;
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public CardResponseDto(
      Long cardId,
      String title,
      String color,
      Long workerCount,
      Long fileCount,
      Long commentCount,
      LocalDateTime startDate,
      LocalDateTime endDate
  ) {
    this.cardId = cardId;
    this.title = title;
    this.color = color;
    this.workerCount = workerCount;
    this.fileCount = fileCount;
    this.commentCount = commentCount;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
