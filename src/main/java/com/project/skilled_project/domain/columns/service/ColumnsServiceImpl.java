package com.project.skilled_project.domain.columns.service;

import com.project.skilled_project.domain.columns.dto.request.ColumnsCreateRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsUpdateNameRequestDto;
import com.project.skilled_project.domain.columns.entity.Columns;
import com.project.skilled_project.domain.columns.repository.ColumnsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ColumnsServiceImpl implements ColumnsService {

  private final ColumnsRepository columnsRepository;

  // 컬럼 생성
  @Override
  public void createColumns(ColumnsCreateRequestDto columnsCreateRequestDto, Long boardId) {
    Columns columns = new Columns(boardId, columnsCreateRequestDto.getTitle());
    columnsRepository.save(columns);
  }

  @Override
  public void updateNameColumns(Long columnId,
      ColumnsUpdateNameRequestDto columnsUpdateNameRequestDto) {
    Columns columns = columnsRepository.findById(columnId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 컬럼입니다."));
    columns.updateNameColumns(columnsUpdateNameRequestDto);
  }

  @Override
  public void deleteColumns(Long columnId) {
    Columns columns = columnsRepository.findById(columnId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 컬럼입니다."));
    columnsRepository.delete(columns);
  }
}
