package com.project.skilled_project.domain.worker.service;

import com.project.skilled_project.domain.worker.dto.WorkerRequestDto;

public interface WorkerService {

  void addWorkers(WorkerRequestDto workerRequestDto);

  void deleteWorkers(WorkerRequestDto workerRequestDto);
}
