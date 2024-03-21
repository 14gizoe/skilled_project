package com.project.skilled_project.domain.worker.service;

import com.project.skilled_project.domain.worker.dto.WorkerRequestDto;
import java.util.List;

public interface WorkerService {

  void addWorkers(WorkerRequestDto workerRequestDto);
  void deleteWorkers(WorkerRequestDto workerRequestDto);

  List<Long> findAllUserIdByCardId(Long cardId);
}
