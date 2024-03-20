package com.project.skilled_project.domain.comment.entity;

import com.project.skilled_project.global.util.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
@SQLRestriction(value = "deleted_at is NULL")
public class Comment extends Timestamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String content;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "card_id")
  private Long cardId;


  public Comment(String content, Long cardId, Long userId) {
    this.content = content;
    this.cardId = cardId;
    this.userId = userId;
  }

  public void update(String content) {
    this.content = content;
  }
}
