package com.project.skilled_project.domain.board.dto.response;

import com.project.skilled_project.domain.card.entity.Card;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

  private String title;
  private LocalDateTime deadline;
  private int numberFiles;
  private int numberComments;
  private int numberMembers;

  public CardDto(Card card) {
    this.title = card.getTitle();
    this.deadline = card.getDeadline();
  }
}
