package com.project.skilled_project.domain.worker.repository;

import com.project.skilled_project.domain.worker.entity.Worker;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

  @Query("select w.userId from Worker w where w.cardId = ?1")
  List<Long> findAllUserIdByCardId(Long cardId);

  @Modifying
  @Query("delete from Worker w where w.userId in :deletingWorkerIds")
  void deleteAllUser(List<Long> deletingWorkerIds);
}
