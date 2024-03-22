package com.project.skilled_project.domain.columns.service;

import com.project.skilled_project.domain.columns.dto.request.ColumnsChangeNumberRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsCreateRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsUpdateNameRequestDto;
import com.project.skilled_project.domain.columns.entity.Columns;
import com.project.skilled_project.domain.columns.repository.ColumnsRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
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
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 컬럼입니다.1"));
    Columns columns1 = columnsRepository.findById(columnsChangeNumberRequestDto.getColumnsId())
        .orElseThrow(() -> new IllegalArgumentException("목표 컬럼이 존재하지 않는 컬럼입니다."));
    if (!Objects.equals(columns.getBoardId(), columns1.getBoardId())) {
      throw new IllegalArgumentException("두 컬럼의 보드가 다릅니다.");
    }
    // 어느 보드에 있는지 알아야함.
    // 수정하려는 컬럼과 같은 보드의 컬럼들을 불러와서 리스트로 만듬
    Long who = columns.getPosition();
    Long where = columns1.getPosition();
    Long gab = 0L;
    List<Columns> columnsList = null;

    if (who < where) {
      columnsList = columnsRepository.findAllByBoardIdOrderByPositionAsc(
          columns.getBoardId());
    } else if (where < who) {
      columnsList = columnsRepository.findAllByBoardIdOrderByPositionDesc(
          columns.getBoardId());
    }

    Queue<Long> positionStack = new LinkedList<>();
    int i = 0;
    while (!Objects.equals(columnsList.get(i).getPosition(), who)) {
      i++;
    }
    positionStack.add(columnsList.get(i).getPosition());
    i++;
    while (!Objects.equals(columnsList.get(i).getPosition(), where)) {
      Columns columnsChange = columnsRepository.findByPosition(
          columnsList.get(i).getPosition());
      positionStack.add(columnsList.get(i).getPosition());
      columnsChange.changePositionColumns(positionStack.poll());
      i++;
    }
    Columns columnsChange = columnsRepository.findByPosition(
        columnsList.get(i).getPosition());
    positionStack.add(columnsList.get(i).getPosition());
    columnsChange.changePositionColumns(positionStack.poll());
    columns.changePositionColumns(positionStack.poll());
  }

  @Override
  public Columns findColumns(Long columnsId){
    return columnsRepository.findById(columnsId).orElseThrow(
        ()-> new EntityNotFoundException("컬럼 없음")
    );
  }

}
