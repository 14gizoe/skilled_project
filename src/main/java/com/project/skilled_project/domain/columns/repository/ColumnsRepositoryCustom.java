package com.project.skilled_project.domain.columns.repository;

public interface ColumnsRepositoryCustom {
  double getCenterColumnsPositionTargetBig(Long boardId, double nowPosition, double targetPosition);
  double getCenterColumnsPositionNowBig(Long boardId, double nowPosition, double targetPosition);

}
