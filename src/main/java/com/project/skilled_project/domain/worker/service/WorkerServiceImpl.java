package com.project.skilled_project.domain.worker.service;

import com.project.skilled_project.domain.board.entity.Board;
import com.project.skilled_project.domain.board.repository.BoardRepository;
import com.project.skilled_project.domain.board.repository.ParticipantRepository;
import com.project.skilled_project.domain.card.entity.Card;
import com.project.skilled_project.domain.card.repository.CardRepository;
import com.project.skilled_project.domain.columns.entity.Columns;
import com.project.skilled_project.domain.columns.repository.ColumnsRepository;
import com.project.skilled_project.domain.worker.dto.WorkerRequestDto;
import com.project.skilled_project.domain.worker.entity.Worker;
import com.project.skilled_project.domain.worker.repository.WorkerRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkerServiceImpl implements WorkerService {

  private final WorkerRepository workerRepository;
  private final BoardRepository boardRepository;
  private final ParticipantRepository participantRepository;
  private final CardRepository cardRepository;
  private final ColumnsRepository columnsRepository;

  @Override
  public void addWorkers(WorkerRequestDto workerRequestDto) {
    Long boardId = workerRequestDto.getBoardId();
    Long columnId = workerRequestDto.getColumnId();
    Long cardId = workerRequestDto.getCardId();

    checkValidCard(boardId, columnId, cardId);

    List<Long> alreadyWorkerIds = getAlreadyWorkerIds(cardId);
    List<Long> participantIds = getPaticipantIds(boardId);
    addWorkersHelper(workerRequestDto.getUserIdList(), participantIds, alreadyWorkerIds, cardId);
  }

  @Override
  public void deleteWorkers(WorkerRequestDto workerRequestDto) {
    Long boardId = workerRequestDto.getBoardId();
    Long columnId = workerRequestDto.getColumnId();
    Long cardId = workerRequestDto.getCardId();

    checkValidCard(boardId, columnId, cardId);

    List<Long> alreadyWorkerIds = getAlreadyWorkerIds(cardId);
    deleteWorkersHelper(workerRequestDto.getUserIdList(), alreadyWorkerIds);
  }

  private void checkValidCard(Long boardId, Long columnId, Long cardId) {
    Card card = cardRepository.findById(cardId).orElseThrow(
        () -> new EntityNotFoundException("해당 카드없음")
    );
    if (!card.getColumnId().equals(columnId)) {
      throw new IllegalArgumentException("컬럼번호 불일치");
    }
    Columns columns = columnsRepository.findById(columnId).orElseThrow(
        ()-> new EntityNotFoundException("해당 컬럼없음")
    );
    if (!columns.getBoardId().equals(boardId)) {
      throw new IllegalArgumentException("보드번호 불일치");
    }
    Board board = boardRepository.findById(boardId).orElseThrow(
        () -> new EntityNotFoundException("해당 보드없음")
    );
  }

  private List<Long> getAlreadyWorkerIds(Long cardId) {
    return workerRepository.findAllUserIdByCardId(cardId);
  }

  private List<Long> getPaticipantIds(Long boardId) {
    return participantRepository.findAllByBoardId(boardId);
  }

  private void addWorkersHelper(List<Long> userIdList, List<Long> participantIds, List<Long> alreadyWorkerIds, Long cardId) {
    Set<Long> participantIdSet = new HashSet<>(participantIds);
    Set<Long> alreadyWorkerIdSet = new HashSet<>(alreadyWorkerIds);
    List<Worker> savingWorkers = new ArrayList<>();

    for (Long userId : userIdList) {
      if (!participantIdSet.contains(userId)) {
        throw new IllegalArgumentException("올바르지 않은 입력이 들어옴");
      }
      if (alreadyWorkerIdSet.contains(userId)) {
        continue;
      }
      savingWorkers.add(new Worker(userId, cardId));
    }
    workerRepository.saveAll(savingWorkers);
  }

  private void deleteWorkersHelper(List<Long> userIdList, List<Long> alreadyWorkerIds) {
    Set<Long> alreadyWorkerIdSet = new HashSet<>(alreadyWorkerIds);
    List<Long> deletingWorkerIds = new ArrayList<>();

    for (Long userId : userIdList) {
      if (!alreadyWorkerIdSet.contains(userId)) {
        throw new IllegalArgumentException("올바르지 않은 입력이 들어옴");
      }
      deletingWorkerIds.add(userId);
    }
    workerRepository.deleteAllUser(deletingWorkerIds);
  }
}
