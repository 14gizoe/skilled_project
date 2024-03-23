package com.project.skilled_project.domain.columns.dto;

import com.project.skilled_project.domain.card.entity.Card;
import com.project.skilled_project.domain.columns.entity.Columns;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ColumnDto {

  private Columns columns;

  private Card cards;
}
