package com.project.skilled_project.domain.board.dto.response;

import com.project.skilled_project.domain.card.dto.response.CardResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColumnDto {

  private String title;
  private List<CardResponseDto> cards;
}
