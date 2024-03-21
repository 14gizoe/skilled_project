package com.project.skilled_project.domain.card.service;

import com.project.skilled_project.domain.card.dto.request.CardCreateRequestDto;
import com.project.skilled_project.domain.card.dto.request.CardUpdateDateRequestDto;
import com.project.skilled_project.domain.card.dto.request.CardUpdateRequestDto;
import com.project.skilled_project.domain.card.dto.response.CardDetailsResponseDto;
import com.project.skilled_project.domain.card.dto.response.CardResponseDto;
import com.project.skilled_project.domain.card.entity.Card;
import java.util.List;

public interface CardService {
  void createCard(CardCreateRequestDto cardCreateRequestDto);

  CardDetailsResponseDto getCard(Long cardId);

  void updateCard(Long cardId, CardUpdateRequestDto cardUpdateRequestDto, Long userId);

  void updateCardDate(Long cardId, CardUpdateDateRequestDto cardUpdateDateRequestDto,  Long userId);

  void deleteCard(Long cardId, Long userId);

  Card findCardById(Long cardId);

  List<CardResponseDto> getCards();

  void commentCountUp(Long cardId);
  void commentCountDown(Long cardId);
}
