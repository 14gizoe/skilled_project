package com.project.skilled_project.domain.card.service;

import com.project.skilled_project.domain.board.entity.Board;
import com.project.skilled_project.domain.board.service.BoardService;
import com.project.skilled_project.domain.card.dto.request.CardCreateRequestDto;
import com.project.skilled_project.domain.card.dto.request.CardUpdateDateRequestDto;
import com.project.skilled_project.domain.card.dto.request.CardUpdateRequestDto;
import com.project.skilled_project.domain.card.dto.response.CardDetailsResponseDto;
import com.project.skilled_project.domain.card.dto.response.CardResponseDto;
import com.project.skilled_project.domain.card.entity.Card;
import com.project.skilled_project.domain.card.repository.CardRepository;
import com.project.skilled_project.domain.columns.entity.Columns;
import com.project.skilled_project.domain.columns.service.ColumnsService;
import com.project.skilled_project.domain.user.entity.User;
import com.project.skilled_project.domain.user.service.UserService;
import com.project.skilled_project.domain.worker.service.WorkerService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{
  private final CardRepository cardRepository;

  private final UserService userService;
  private final WorkerService workerService;
  private final ColumnsService columnsService;
  private final BoardService boardService;

  @Override
  public void createCard(CardCreateRequestDto cardCreateRequestDto) {
    Columns columns = columnsService.findColumns(cardCreateRequestDto.getColumnId());
    Board board = boardService.findBoard(cardCreateRequestDto.getBoardId());
    Card card = new Card(cardCreateRequestDto);
    cardRepository.save(card);
  }

  @Override
  public CardDetailsResponseDto getCard(Long cardId) {
    Card card = cardRepository.findById(cardId).orElseThrow(
        () -> new EntityNotFoundException("카드 없음")
    );
    return cardRepository.getQueryCard(cardId);
  }

  @Override
  public List<CardResponseDto> getCards() {
    return cardRepository.getQueryCards();
  }

  @Override
  public void updateCard(Long cardId, CardUpdateRequestDto cardUpdateRequestDto, Long userId) {
    User user = userService.findUser(userId);
    Card card = cardRepository.findById(cardId).orElseThrow(
        () -> new EntityNotFoundException("카드 없음")
    );
    if (isWorkernotInCard(user.getId(), cardId)) {
      throw new IllegalArgumentException("카드 수정권한 없음");
    }
    card.update(cardUpdateRequestDto);
  }

  @Override
  public void updateCardDate(
      Long cardId,
      CardUpdateDateRequestDto cardUpdateDateRequestDto,
      Long userId
  ) {
    User user = userService.findUser(userId);
    Card card = cardRepository.findById(cardId).orElseThrow(
        () -> new EntityNotFoundException("카드 없음")
    );
    if (isWorkernotInCard(user.getId(), cardId)) {
      throw new IllegalArgumentException("목표기간 수정권한 없음");
    }
    card.updateDate(cardUpdateDateRequestDto);
  }

  @Override
  public void deleteCard(Long cardId, Long userId) {
    User user = userService.findUser(userId);
    Card card = cardRepository.findById(cardId).orElseThrow(
        () -> new EntityNotFoundException("카드 없음")
    );

    if (isWorkernotInCard(user.getId(), cardId)) {
      throw new IllegalArgumentException("카드 삭제권한 없음");
    }
    cardRepository.delete(card);
  }

  @Override
  public Card findCardById(Long cardId) {
    return  cardRepository.findById(cardId).orElseThrow(
        () -> new EntityNotFoundException("카드 없음")
    );
  }

  private boolean isWorkernotInCard(Long userId, Long cardId) {
    List<Long> workerIds = workerService.findAllUserIdByCardId(cardId);
    for (Long workerId : workerIds) {
      if (userId.equals(workerId)) {
        return false;
      }
    }
    return true;
  }
}
