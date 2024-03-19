package com.project.skilled_project.domain.board.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardsResponseDto {

  private List<String> boardTitle = new ArrayList<>();

  public void boardTitleUpdate(String title) {
    boardTitle.add(title);
  }
}
