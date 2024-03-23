package com.project.skilled_project.domain.columns.repository;

import com.project.skilled_project.domain.columns.entity.Columns;
import java.util.List;

public interface ColumnsRepositoryCustom {
  Long getCenterColumnsPositionTargetBig(Long boardId, Long nowPosition, Long targetPosition);
  Long getCenterColumnsPositionNowBig(Long boardId, Long nowPosition, Long targetPosition);
  List<Columns> getAllColumns (Long boardId);

}
