package com.project.skilled_project.domain.checklist.entity;


import com.project.skilled_project.domain.checklist.dto.ChecklistDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Checklist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(nullable = false)
  String content;
  @Column(nullable = false)
  Boolean completed = false;
  @Column(nullable = false)
  Long cardId;

  public Checklist(ChecklistDto checkListDto) {
    this.cardId = checkListDto.getCardId();
    this.content = checkListDto.getContent();
  }

  public void toggle() {
    this.completed = !this.completed;
  }
}
