package com.project.skilled_project.domain.card.dto.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardUpdateDateRequestDto {
  private LocalDateTime startDate;
  private LocalDateTime endDate;
}
