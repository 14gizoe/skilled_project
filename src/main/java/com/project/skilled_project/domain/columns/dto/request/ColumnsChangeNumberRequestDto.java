package com.project.skilled_project.domain.columns.dto.request;

import lombok.Getter;

@Getter
public class ColumnsChangeNumberRequestDto {

  // 어느 컬럼 아래로 이동할 것인가 0이면 맨위
  private Long columnsId;


}
