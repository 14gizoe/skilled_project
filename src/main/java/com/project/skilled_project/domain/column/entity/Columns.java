package com.project.skilled_project.domain.column.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Columns {

  @Id
  @GeneratedValue
  @Column(name = "columns_id", nullable = false)
  private Long id;
  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "board_id", nullable = false)
  private Long boardId;

}
