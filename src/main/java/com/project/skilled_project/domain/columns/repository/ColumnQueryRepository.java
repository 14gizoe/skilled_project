package com.project.skilled_project.domain.columns.repository;

import com.project.skilled_project.domain.card.dto.response.CardResponseDto;
import com.project.skilled_project.domain.card.entity.Card;
import com.project.skilled_project.domain.card.entity.QCard;
import com.project.skilled_project.domain.columns.dto.ColumnDto;
import com.project.skilled_project.domain.columns.dto.response.ColumnResponseDto;
import com.project.skilled_project.domain.columns.entity.Columns;
import com.project.skilled_project.domain.columns.entity.QColumns;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ColumnQueryRepository {

  private final JPAQueryFactory query;

  public List<ColumnDto> getColumns() {
    QColumns columns = QColumns.columns;
    QCard card = QCard.card;
    return query.select(
            Projections.constructor(
                ColumnDto.class,
                columns,
                card
            )
        )
        .from(columns)
        .leftJoin(card)
        .on(columns.columnsId.eq(card.columnId))
        .fetch();
  }

}
