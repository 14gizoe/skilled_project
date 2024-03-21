package com.project.skilled_project.domain.card.repository;

import com.project.skilled_project.domain.card.dto.response.CardDetailsResponseDto;
import com.project.skilled_project.domain.card.dto.response.CardResponseDto;
import java.util.List;

public interface CardQuerydsl {
  CardDetailsResponseDto getQueryCard(Long cardId);

  List<CardResponseDto> getQueryCards();
}
