package com.project.skilled_project.domain.card.dto.response;

import com.project.skilled_project.domain.card.entity.Card;
import com.project.skilled_project.domain.checklist.dto.response.ChecklistResponseDto;
import com.project.skilled_project.domain.comment.dto.response.CommentResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardDetailsResponseDto {

  private Long cardId;
  private String title;
  private String description;
  private String color;
  private Long userCount;
  private Long fileCount;
  private Long commentCount;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private List<String> workers;
  private List<ChecklistResponseDto> checklists;
  private List<CommentResponseDto> comments;

  public CardDetailsResponseDto(
      Card card, List<String> workers,
      List<ChecklistResponseDto> checklists, List<CommentResponseDto> comments
  ) {
    this.cardId = card.getId();
    this.title = card.getTitle();
    this.description = card.getDescription();
    this.color = card.getColor();
    this.userCount = card.getUserCount();
    this.fileCount = card.getFileCount();
    this.commentCount = card.getCommentCount();
    this.startDate = card.getStartDate();
    this.endDate = card.getEndDate();
    this.workers = workers;
    this.checklists = checklists;
    this.comments = comments;
  }
}
