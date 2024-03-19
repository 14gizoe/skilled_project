package com.project.skilled_project.domain.board.dto.response;

import com.project.skilled_project.domain.card.entity.Card;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
  private String title;
  private Date deadline;
  private int numberFiles;
  private int numberComments;
  private int numberMembers;

  public CardDto(Card card){
    this.title = card.getTitle();
  }
}
