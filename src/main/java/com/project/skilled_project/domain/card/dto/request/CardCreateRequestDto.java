package com.project.skilled_project.domain.card.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardCreateRequestDto {
  private Long columnId;
  private Long boardId;
  private String title;
  private String description;
}
