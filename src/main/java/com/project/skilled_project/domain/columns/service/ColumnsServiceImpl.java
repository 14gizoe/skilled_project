package com.project.skilled_project.domain.columns.service;

import com.project.skilled_project.domain.columns.dto.request.ColumnsChangeNumberRequestDto;
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
  public void createColumns(ColumnsCreateRequestDto columnsCreateRequestDto) {
    Columns columns = new Columns(columnsCreateRequestDto);
    columnsRepository.save(columns);
  }

  @Override
  public void updateNameColumns(Long columnsId,
      ColumnsUpdateNameRequestDto columnsUpdateNameRequestDto) {
    Columns columns = columnsRepository.findById(columnsId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 컬럼입니다."));
    columns.updateNameColumns(columnsUpdateNameRequestDto);
  }

  @Override
  public void deleteColumns(Long columnsId) {
    Columns columns = columnsRepository.findById(columnsId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 컬럼입니다."));
    columnsRepository.delete(columns);
  }

  @Override
  public void changeNumberColumns(Long columnsId,
      ColumnsChangeNumberRequestDto columnsChangeNumberRequestDto) {
    // 현재 컬럼의 데이터
    Columns columns = columnsRepository.findById(columnsId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 컬럼입니다."));
    columns.changeNumberColumns(0L);// 일단 현재 컬럼의 데이터를 0번으로 옮김


    if (columnsId < columnsChangeNumberRequestDto.getColumnsId()) {
      // 현재 컬럼을 아래로 내릴 때
      Long count = (columnsChangeNumberRequestDto.getColumnsId() - columnsId);

      for (Long i = 1L; i < count; i++) {
        if (columnsRepository.findByColumnsId(columnsId + i) != null) {
          Columns columnsChange = columnsRepository.findByColumnsId(columnsId + i);
          columnsChange.changeNumberColumns(columnsId + i - 1L); // 1 씩 넘버값을 감소시킴
        }
      }
    } else {
      // 컬럼을 위로 올릴때
      Long count = (columnsId - columnsChangeNumberRequestDto.getColumnsId());

      for (Long i = 1L; i < count; i++) {
        if (columnsRepository.findByColumnsId(columnsId - i) != null) {
          Columns columnsChange = columnsRepository.findByColumnsId(columnsId + i);
          columnsChange.changeNumberColumns(columnsId - i + 1L); // 1 씩 넘버값을 증가시킴
        }
      }
      // 기존 컬럼은 입력할 변경할 컬럼위치로 이동
      columns.changeNumberColumns(columnsChangeNumberRequestDto.getColumnsId());
    }
  }
}