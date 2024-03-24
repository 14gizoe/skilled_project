package com.project.skilled_project.domain.columns.dto.response;

import com.project.skilled_project.domain.card.dto.response.CardResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ColumnResponseDto {

  private Long columnsId;

  private Long boardId;

  private String title;

  private Long position;

  private List<CardResponseDto> cards;
}

