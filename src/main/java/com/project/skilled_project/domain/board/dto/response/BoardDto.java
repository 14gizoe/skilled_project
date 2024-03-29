package com.project.skilled_project.domain.board.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {

  private String title;
  private String color;
  private List<String> invitedUsers;
  private List<ColumnDto> columns;

  public BoardDto(String title, String color,List<String> usernames, List<ColumnDto> columnDtoList) {
    this.title = title;
    this.color = color;
    invitedUsers = usernames;
    this.columns = columnDtoList;
  }
}
