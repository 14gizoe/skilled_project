package com.project.skilled_project.domain.card.dto.response;

import com.project.skilled_project.domain.card.entity.Card;
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
      Card card
  ) {
    this.cardId = card.getId();
    this.title = card.getTitle();
    this.color = card.getColor();
    this.workerCount = card.getWorkerCount();
    this.fileCount = card.getFileCount();
    this.commentCount = card.getCommentCount();
    this.startDate = card.getStartDate();
    this.endDate = card.getEndDate();
  }
}
