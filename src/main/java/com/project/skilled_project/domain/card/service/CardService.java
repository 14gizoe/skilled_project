package com.project.skilled_project.domain.card.service;

import com.project.skilled_project.domain.card.dto.request.CardCreateRequestDto;
import com.project.skilled_project.domain.card.dto.request.CardUpdateDateRequestDto;
import com.project.skilled_project.domain.card.dto.request.CardUpdateRequestDto;
import com.project.skilled_project.domain.card.entity.Card;

public interface CardService {
  void createCard(CardCreateRequestDto cardCreateRequestDto);

  void updateCard(Long cardId, CardUpdateRequestDto cardUpdateRequestDto, Long userId);

  void deleteCard(Long cardId, Long userId);

  void updateCardDate(Long cardId, CardUpdateDateRequestDto cardUpdateDateRequestDto,  Long userId);

  Card findCardById(Long cardId);
}
