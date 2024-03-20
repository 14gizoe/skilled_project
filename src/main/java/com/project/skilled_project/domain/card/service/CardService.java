package com.project.skilled_project.domain.card.service;


import com.project.skilled_project.domain.card.dto.request.CardCreateRequestDto;

public interface CardService {
  void createCard(CardCreateRequestDto cardCreateRequestDto);
}
