package com.project.skilled_project.domain.columns.service;

import com.project.skilled_project.domain.board.repository.BoardRepository;
import com.project.skilled_project.domain.columns.dto.request.ColumnsCreateRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsUpdateNameRequestDto;
import com.project.skilled_project.domain.columns.entity.Columns;
import com.project.skilled_project.domain.columns.repository.ColumnsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColumnsServiceImpl implements ColumnsService {

  private final ColumnsRepository columnsRepository;
  private final BoardRepository boardRepository;

  // 컬럼 생성
  @Override
  public void createColumns(ColumnsCreateRequestDto columnsCreateRequestDto, Long boardId) {
    Columns columns = new Columns(boardId, columnsCreateRequestDto.getTitle());
    columnsRepository.save(columns);
  }

  @Override
  public void updateNameColumn(Long columnId,
      ColumnsUpdateNameRequestDto columnsUpdateNameRequestDto) {
    Columns columns = columnsRepository.findById(columnId).orElseThrow();
    columns.updateNameColumns(columnsUpdateNameRequestDto);
  }

  @Override
  public void deleteColumn() {

  }
}
