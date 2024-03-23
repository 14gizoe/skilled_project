package com.project.skilled_project.domain.columns.dto.request;

import lombok.Getter;

@Getter
public class ColumnsCreateRequestDto {

  private Long boardId;
  private String title;
  private Long position;

}
