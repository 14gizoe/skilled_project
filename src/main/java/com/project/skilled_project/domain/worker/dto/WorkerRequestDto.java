package com.project.skilled_project.domain.worker.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class WorkerRequestDto {
  private Long boardId;
  private Long columnId;
  private Long cardId;
  private List<Long> userIdList;
}
