package com.project.skilled_project.domain.card.entity;


import com.project.skilled_project.domain.card.dto.request.CardCreateRequestDto;
import com.project.skilled_project.global.util.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Card extends Timestamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private Long columnId;
  @Column(nullable = false)
  private Long boardId;
  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private String color = "#FFFFFF";
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public Card(CardCreateRequestDto cardCreateRequestDto) {
    this.columnId = cardCreateRequestDto.getColumnId();
    this.boardId = cardCreateRequestDto.getBoardId();
    this.title = cardCreateRequestDto.getTitle();
    this.description = cardCreateRequestDto.getDescription();
  }
}
