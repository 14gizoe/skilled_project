package com.project.skilled_project.domain.columns.service;

import com.project.skilled_project.domain.columns.dto.request.ColumnsCreateRequestDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ColumnsService {

  // 컬럼 생성
  public void createColumns(ColumnsCreateRequestDto columnsCreateRequestDto, Long boardId);
  public void updateNameColumn();
  public void deleteColumn();


}
