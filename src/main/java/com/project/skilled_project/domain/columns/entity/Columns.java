package com.project.skilled_project.domain.columns.entity;


import com.project.skilled_project.domain.columns.dto.request.ColumnsCreateRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsUpdateNameRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Columns {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "columns_id", nullable = false)
  private Long columnsId;
  @Column(name = "title", nullable = false)
  private String title;
  @Column(name = "board_id", nullable = false)
  private Long boardId;
  @Column(name = "position", nullable = false)
  private double position;


  public Columns(ColumnsCreateRequestDto columnsCreateRequestDto) {
    this.boardId = columnsCreateRequestDto.getBoardId();
    this.title = columnsCreateRequestDto.getTitle();
    this.position = (columnsCreateRequestDto.getPosition()*1024);
  }

  public void updateNameColumns(ColumnsUpdateNameRequestDto columnsUpdateNameRequestDto) {
    this.title = columnsUpdateNameRequestDto.getTitle();
  }

  public void changePositionColumns(double position) {
    this.position = position;
  }
}
