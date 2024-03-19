package com.project.skilled_project.domain.board.dto.response;

import com.project.skilled_project.domain.column.entity.Columns;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
  private String title;
  private String color;
  private List<ColumDto> columns;
}
