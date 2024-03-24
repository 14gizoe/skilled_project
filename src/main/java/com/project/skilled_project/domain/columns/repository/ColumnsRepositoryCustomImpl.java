package com.project.skilled_project.domain.columns.repository;

import static com.project.skilled_project.domain.columns.entity.QColumns.columns;

import com.project.skilled_project.domain.columns.entity.Columns;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ColumnsRepositoryCustomImpl implements ColumnsRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;


  @Override
  public Long getCenterColumnsPositionTargetBig(Long boardId, Long nowPosition,
      Long targetPosition) {
    return jpaQueryFactory
        .select(columns.position)
        .from(columns)
        .where(columns.boardId.eq(boardId))
        .where(columns.position.goe(nowPosition))
        .where(columns.position.lt(targetPosition))
        .orderBy(columns.position.desc())
        .fetchFirst();
  }

  @Override
  public Long getCenterColumnsPositionNowBig(Long boardId, Long nowPosition, Long targetPosition) {
    return jpaQueryFactory
        .select(columns.position)
        .from(columns)
        .where(columns.boardId.eq(boardId))
        .where(columns.position.lt(nowPosition))
        .where(columns.position.gt(targetPosition))
        .orderBy(columns.position.asc())
        .fetchFirst();
  }
  @Override
  public List<Columns> getAllColumns(Long boardId){
    return jpaQueryFactory
        .select(columns)
        .from(columns)
        .where(columns.boardId.eq(boardId))
        .orderBy(columns.position.asc())
        .fetch();
  }
}
