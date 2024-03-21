package com.project.skilled_project.domain.card.controller;

import com.project.skilled_project.domain.card.dto.request.CardCreateRequestDto;
import com.project.skilled_project.domain.card.dto.request.CardUpdateDateRequestDto;
import com.project.skilled_project.domain.card.dto.request.CardUpdateRequestDto;
import com.project.skilled_project.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class CardController {

  private final CardService cardService;
  @PostMapping
  public void createCard(@RequestBody CardCreateRequestDto cardCreateRequestDto) {
    cardService.createCard(cardCreateRequestDto);
  }

  @PutMapping("/{cardId}")
  public void updateCard(@PathVariable Long cardId, @RequestBody CardUpdateRequestDto cardUpdateRequestDto,
                         @AuthenticationPrincipal UserDetails userDetails) {
    cardService.updateCard(cardId, cardUpdateRequestDto, userDetails.getUsername());
  }


  @PatchMapping("/{cardId}/date")
  public void updateCardDate(@PathVariable Long cardId, @RequestBody CardUpdateDateRequestDto cardUpdateDateRequestDto,
                          @AuthenticationPrincipal UserDetails userDetails) {
    cardService.updateCardDate(cardId, cardUpdateDateRequestDto, userDetails.getUsername());
  }
  @DeleteMapping("/{cardId}")
  public void deleteCard(@PathVariable Long cardId, @AuthenticationPrincipal UserDetails userDetails) {
    cardService.deleteCard(cardId, userDetails.getUsername());
  }
}
