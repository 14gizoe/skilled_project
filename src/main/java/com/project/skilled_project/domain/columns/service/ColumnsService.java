package com.project.skilled_project.domain.columns.service;

import com.project.skilled_project.domain.columns.dto.request.ColumnsChangeNumberRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsCreateRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsUpdateNameRequestDto;
import com.project.skilled_project.domain.columns.entity.Columns;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ColumnsService {

  // 컬럼 생성
  public void createColumns(ColumnsCreateRequestDto columnsCreateRequestDto);

  public void updateNameColumns(Long columnsId,
      ColumnsUpdateNameRequestDto columnsUpdateNameRequestDto);

  public void deleteColumns(Long columnsId);

  public void changeNumberColumns(Long columnsId,
      ColumnsChangeNumberRequestDto columnsChangeNumberRequestDto);

  public Columns findColumns(Long columnsId);



}
