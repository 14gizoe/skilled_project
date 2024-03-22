package com.project.skilled_project.domain.columns.service;

import com.project.skilled_project.domain.columns.dto.request.ColumnsChangeNumberRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsCreateRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsUpdateNameRequestDto;
import com.project.skilled_project.domain.columns.entity.Columns;
import com.project.skilled_project.domain.columns.repository.ColumnsRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
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
    Columns columns = findColumns(columnsId);
    columns.updateNameColumns(columnsUpdateNameRequestDto);
  }


  @Override
  public void deleteColumns(Long columnsId) {
    Columns columns = findColumns(columnsId);
    columnsRepository.delete(columns);
  }

  @Override
  public void changeNumberColumns(Long columnsId,
      ColumnsChangeNumberRequestDto columnsChangeNumberRequestDto) {
    // 현재 컬럼의 데이터
    Columns nowColumns = findColumns(columnsId);
    Columns targetColumns = findColumns(columnsChangeNumberRequestDto.getColumnsId());
    if (!Objects.equals(nowColumns.getBoardId(), targetColumns.getBoardId())) {
      throw new IllegalArgumentException("두 컬럼의 보드가 다릅니다.");
    }
    // 어느 보드에 있는지 알아야함.
    // 수정하려는 컬럼과 같은 보드의 컬럼들을 불러와서 리스트로 만듬
    Long who = nowColumns.getPosition();
    Long where = targetColumns.getPosition();
    List<Columns> columnsList = new ArrayList<>();

    if (who < where) {
      columnsList = columnsRepository.findAllByBoardIdOrderByPositionAsc(
          nowColumns.getBoardId());
    } else if (where < who) {
      columnsList = columnsRepository.findAllByBoardIdOrderByPositionDesc(
          nowColumns.getBoardId());
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
    nowColumns.changePositionColumns(positionStack.poll());

  }
  @Override
  public Columns findColumns(Long columnsId){
    return columnsRepository.findById(columnsId).orElseThrow(
        ()-> new EntityNotFoundException("컬럼 없음")
    );
  }

}
