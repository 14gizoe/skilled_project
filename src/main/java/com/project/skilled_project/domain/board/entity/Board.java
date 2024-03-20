package com.project.skilled_project.domain.board.entity;

import com.project.skilled_project.domain.board.dto.request.BoardRequestDto;
import com.project.skilled_project.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor
@SQLRestriction("deleted_at is NULL")
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "color", nullable = false)
  private String color = "#FFFFFF";

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  public Board(BoardRequestDto req, User user) {
    this.title = req.getTitle();
    this.userId = user.getId();
  }

  public void update(BoardRequestDto req) {
    this.title = req.getTitle();
    this.color = req.getColor();
  }

  public void softDelete() {
    this.deletedAt = LocalDateTime.now();
  }

}
