package com.project.skilled_project.domain.board.dto.response;

import com.project.skilled_project.domain.column.entity.Columns;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColumDto {
  private String title;
  private List<CardDto> cards;
}
