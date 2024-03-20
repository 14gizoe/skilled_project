package com.project.skilled_project.domain.card.service;

import com.project.skilled_project.domain.card.dto.request.CardCreateRequestDto;
import com.project.skilled_project.domain.card.dto.request.CardUpdateRequestDto;

public interface CardService {
  void createCard(CardCreateRequestDto cardCreateRequestDto);

  void updateCard(Long cardId, CardUpdateRequestDto cardUpdateRequestDto, String username);

  void deleteCard(Long cardId, String username);
}
