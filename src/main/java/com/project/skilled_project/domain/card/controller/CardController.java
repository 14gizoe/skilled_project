package com.project.skilled_project.domain.card.controller;

import com.project.skilled_project.domain.card.dto.request.CardCreateRequestDto;
import com.project.skilled_project.domain.card.dto.request.CardUpdateDateRequestDto;
import com.project.skilled_project.domain.card.dto.request.CardUpdateRequestDto;
import com.project.skilled_project.domain.card.dto.response.CardDetailsResponseDto;
import com.project.skilled_project.domain.card.dto.response.CardResponseDto;
import com.project.skilled_project.domain.card.service.CardService;
import com.project.skilled_project.global.response.CommonResponse;
import com.project.skilled_project.global.util.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("/{cardId}")
  public ResponseEntity<CommonResponse<CardDetailsResponseDto>> getCard(@PathVariable Long cardId) {
    return CommonResponse.ok(cardService.getCard(cardId));
  }

  @GetMapping
  public ResponseEntity<CommonResponse<List<CardResponseDto>>> getCards() {
    return CommonResponse.ok(cardService.getCards());
  }
  @PutMapping("/{cardId}")
  public void updateCard(@PathVariable Long cardId, @RequestBody CardUpdateRequestDto cardUpdateRequestDto,
                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
    cardService.updateCard(cardId, cardUpdateRequestDto, userDetails.getUser().getId());
  }

  @PatchMapping("/{cardId}/date")
  public void updateCardDate(@PathVariable Long cardId, @RequestBody CardUpdateDateRequestDto cardUpdateDateRequestDto,
                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
    cardService.updateCardDate(cardId, cardUpdateDateRequestDto, userDetails.getUser().getId());
  }

  @DeleteMapping("/{cardId}")
  public void deleteCard(@PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    cardService.deleteCard(cardId, userDetails.getUser().getId());
  }
}
