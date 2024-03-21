package com.project.skilled_project.domain.worker.controller;

import com.project.skilled_project.domain.worker.dto.WorkerRequestDto;
import com.project.skilled_project.domain.worker.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/workers")
public class WorkerController {

  private final WorkerService workerService;
  @PostMapping
  public void addWorkers(@RequestBody WorkerRequestDto workerRequestDto) {
    workerService.addWorkers(workerRequestDto);
  }

  @DeleteMapping
  public void deleteWorkers(@RequestBody WorkerRequestDto workerRequestDto) {
    workerService.deleteWorkers(workerRequestDto);
  }
}
