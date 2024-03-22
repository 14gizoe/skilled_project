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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
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
  private final StringRedisTemplate stringRedisTemplate;

  @Override
  public void createCard(CardCreateRequestDto cardCreateRequestDto) {
    Columns columns = columnsService.findColumns(cardCreateRequestDto.getColumnId());
    Board board = boardService.findBoard(cardCreateRequestDto.getBoardId());
    Card card = new Card(cardCreateRequestDto);
    Card savedCard = cardRepository.save(card);
    initializeCounter(savedCard.getId());
  }

  public void initializeCounter(Long cardId) {
    ValueOperations<String, String> ops = this.stringRedisTemplate.opsForValue();
    ops.set(cardId + "", "0");
  }

  @Scheduled(fixedDelay = 1000 * 60 * 60)
  @Transactional
  public void processTasks() {
    List<Card> cardList = cardRepository.findAll();

    for (Card card : cardList) {
      card.updateCommentCount(Long.parseLong(getCounter(card.getId())));
    }
  }

  @Override
  @Cacheable(value = "Card", key = "#cardId", cacheManager = "cacheManager", unless = "#result == null")
  public CardDetailsResponseDto getCard(Long cardId) {
    Card card = cardRepository.findById(cardId).orElseThrow(
        () -> new EntityNotFoundException("카드 없음")
    );
    CardDetailsResponseDto cardDetailsResponseDto = cardRepository.getQueryCard(card.getId());
    cardDetailsResponseDto.updateCommentCount(Long.parseLong(getCounter(cardId)));
    return cardDetailsResponseDto;
  }

  public String getCounter(Long cardId) {
    ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
    return ops.get(cardId+"");
  }

  @Override
  @Cacheable(value = "CardList", key = "'all'", cacheManager = "cacheManager", unless = "#result == null")
  public List<CardResponseDto> getCards() {
    return cardRepository.getQueryCards();
  }

  @Override
  @CachePut(value = "Card", key = "#cardId", cacheManager = "cacheManager")
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
  @CacheEvict(value = "Card", key = "#cardId", cacheManager = "cacheManager")
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

  @Override
  public void commentCountUp(Long cardId) {
    Card card = cardRepository.findById(cardId).orElseThrow(
        () -> new EntityNotFoundException("카드 없음")
    );
    increaseCount(cardId);
//    card.commentCountUp();
  }

  public void increaseCount(Long cardId) {
    ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
    ops.increment(cardId + "");
  }

  @Override
  public void commentCountDown(Long cardId) {
    Card card = cardRepository.findById(cardId).orElseThrow(
        () -> new EntityNotFoundException("카드 없음")
    );
    card.commentCountDown();
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
