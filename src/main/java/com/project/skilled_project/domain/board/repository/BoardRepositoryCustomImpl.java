package com.project.skilled_project.domain.board.repository;

import static com.project.skilled_project.domain.board.entity.QBoard.board;
import static com.project.skilled_project.domain.card.entity.QCard.card;
import static com.project.skilled_project.domain.column.entity.QColumns.columns;

import com.project.skilled_project.domain.board.dto.response.BoardResponseDto;
import com.project.skilled_project.domain.board.dto.response.BoardsDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<BoardsDto> getBoards() {
    return jpaQueryFactory
        .select(Projections.constructor(BoardsDto.class, board.title))
        .from(board)
        .fetch();
  }

  @Override
  public List<BoardResponseDto> getBoard(Long boardId) {
    return jpaQueryFactory
        .select(
            Projections.constructor(
                BoardResponseDto.class,
                board.title.as("boardTitle"),
                board.color.as("boardColor"),
                columns,
                card
            )
        )
        .from(board)
        .leftJoin(columns)
        .on(board.id.eq(columns.boardId))
        .leftJoin(card)
        .on(columns.id.eq(card.columnId))
        .where(board.id.eq(boardId))
        .fetch();
  }

}
