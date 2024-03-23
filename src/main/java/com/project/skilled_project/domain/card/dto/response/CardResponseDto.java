package com.project.skilled_project.domain.card.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.project.skilled_project.domain.card.entity.Card;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardResponseDto {
  private Long cardId;
  private String title;
  private String color;
  private Long workerCount;
  private Long fileCount;
  private Long commentCount;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
  private LocalDateTime startDate;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
  private LocalDateTime endDate;

  public CardResponseDto(
      Card card
  ) {
    this.cardId = card.getId();
    this.title = card.getTitle();
    this.color = card.getColor();
    this.workerCount = card.getWorkerCount();
    this.fileCount = card.getFileCount();
    this.commentCount = card.getCommentCount();
    this.startDate = card.getStartDate();
    this.endDate = card.getEndDate();
  }
}
