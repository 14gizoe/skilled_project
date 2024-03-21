package com.project.skilled_project.domain.card.repository;

import com.project.skilled_project.domain.card.dto.response.CardDetailsResponseDto;

public interface CardQuerydsl {
  CardDetailsResponseDto getQueryCard(Long cardId);
}
