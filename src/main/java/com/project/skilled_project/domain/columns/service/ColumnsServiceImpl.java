package com.project.skilled_project.domain.columns.service;

import com.project.skilled_project.domain.card.dto.response.CardResponseDto;
import com.project.skilled_project.domain.columns.dto.ColumnDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsChangeNumberRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsCreateRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsUpdateNameRequestDto;
import com.project.skilled_project.domain.columns.dto.response.ColumnResponseDto;
import com.project.skilled_project.domain.columns.entity.Columns;
import com.project.skilled_project.domain.columns.repository.ColumnQueryRepository;
import com.project.skilled_project.domain.columns.repository.ColumnsRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ColumnsServiceImpl implements ColumnsService {

  private final ColumnsRepository columnsRepository;
  private final ColumnQueryRepository columnQueryRepository;

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
    System.out.println("시발");
    System.out.println(columnsId);
    System.out.println(columnsChangeNumberRequestDto.getColumnsId());
    // 현재 컬럼의 데이터
    Columns nowColumns = findColumns(columnsId);
    Columns targetColumns = findColumns(columnsChangeNumberRequestDto.getColumnsId());
    if (!Objects.equals(nowColumns.getBoardId(), targetColumns.getBoardId())) {
      throw new RuntimeException("두 컬럼의 보드가 다릅니다");
    }

    // 두 컬럼 사이에서 이동할 값 찾기
    Long centerColumnsPosition = getCenterColumnsPosition(nowColumns.getPosition(),
        targetColumns.getPosition(), nowColumns.getBoardId());
    long newColumnsPosition = (centerColumnsPosition + targetColumns.getPosition()) / 2;
    nowColumns.changePositionColumns(newColumnsPosition);
    if ((newColumnsPosition > targetColumns.getPosition() &&
        newColumnsPosition <= targetColumns.getPosition() + 100L) ||
        (newColumnsPosition < targetColumns.getPosition() &&
            newColumnsPosition + 100L >= targetColumns.getPosition())) {
      rePosition(nowColumns.getBoardId());
    }
  }

  public void rePosition(Long boardID) {
    List<Columns> columnsList = columnsRepository.getAllColumns(boardID);
    for (int i = 0; i < columnsList.size(); i++) {
      columnsList.get(i).changePositionColumns((i + 1) * 1024L);
    }
  }

  public Long getCenterColumnsPosition(Long nowColumnsPosition, Long targetColumnsPosition,
      Long boardId) {
    if (nowColumnsPosition <= targetColumnsPosition) {
      return columnsRepository.getCenterColumnsPositionTargetBig(boardId, nowColumnsPosition,
          targetColumnsPosition);
    } else {
      return columnsRepository.getCenterColumnsPositionNowBig(boardId, nowColumnsPosition,
          targetColumnsPosition);
    }
  }

  @Override
  public Columns findColumns(Long columnsId) {
    return columnsRepository.findById(columnsId).orElseThrow(
        () -> new EntityNotFoundException("컬럼 없음")
    );
  }

  @Override
  @Cacheable(value = "List<ColumnReponseDto>", key = "'all'", cacheManager = "cacheManager", unless = "#result == null")
  public List<ColumnResponseDto> getColumns() {
    List<ColumnDto> columnList = columnQueryRepository.getColumns();
    Map<Long, List<CardResponseDto>> groupedDataMap = new HashMap<>();
    for (ColumnDto columnDto : columnList) {
      Long columnId = columnDto.getCards().getColumnId();
      List<CardResponseDto> groupedDataList = groupedDataMap.getOrDefault(columnId,
          new ArrayList<>());
      groupedDataList.add(
          new CardResponseDto(columnDto.getCards())
      );
      groupedDataMap.put(columnId, groupedDataList);
    }

    List<ColumnResponseDto> resultList = new ArrayList<>();
    for (ColumnDto columnDto : columnList) {
      resultList.add(new ColumnResponseDto(
          columnDto.getColumns().getColumnsId(),
          columnDto.getColumns().getBoardId(),
          columnDto.getColumns().getTitle(),
          columnDto.getColumns().getPosition(),
          groupedDataMap.get(columnDto.getColumns().getColumnsId())
      ));
    }
    return resultList;
  }
}
