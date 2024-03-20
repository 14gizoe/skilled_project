package com.project.skilled_project.domain.board.dto.response;

import com.project.skilled_project.domain.card.entity.Card;
import com.project.skilled_project.domain.columns.entity.Columns;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {

  private String boardTitle;
  private String boardColor;
  //  private List<String> invitedUsers;
  private Columns columns;
  private Card card;
}
