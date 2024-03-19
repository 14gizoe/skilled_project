package com.project.skilled_project.domain.columns.entity;


import com.project.skilled_project.domain.columns.dto.request.ColumnsCreateRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsUpdateNameRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Columns {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "columns_id", nullable = false)
  private Long id;
  @Column(name = "title", nullable = false)
  private String title;
  @Column(name = "board_id", nullable = false)
  private Long boardId;

  public Columns(ColumnsCreateRequestDto columnsCreateRequestDto) {
    this.boardId = columnsCreateRequestDto.getBoardId();
    this.title = columnsCreateRequestDto.getTitle();
  }

  public void updateNameColumns(ColumnsUpdateNameRequestDto columnsUpdateNameRequestDto) {
    this.title = columnsUpdateNameRequestDto.getTitle();
  }

}
