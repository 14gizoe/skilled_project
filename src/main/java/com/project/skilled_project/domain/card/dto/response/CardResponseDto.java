package com.project.skilled_project.domain.card.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CardResponseDto {
  private Long cardId;
  private String title;
  private String description;
  private String color;
  private Long userCount;
  private Long fileCount;
  private Long commentCount;
  private LocalDateTime endDate;

  public CardResponseDto(
      Long cardId,
      String title,
      String description,
      String color,
      Long userCount,
      Long fileCount,
      Long commentCount,
      LocalDateTime endDate
  ) {
    this.cardId = cardId;
    this.title = title;
    this.description = description;
    this.color = color;
    this.userCount = userCount;
    this.fileCount = fileCount;
    this.commentCount = commentCount;
    this.endDate = endDate;
  }
}
