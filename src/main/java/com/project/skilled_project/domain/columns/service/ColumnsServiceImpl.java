package com.project.skilled_project.domain.columns.service;

import com.project.skilled_project.domain.columns.dto.request.ColumnsChangeNumberRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsCreateRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsUpdateNameRequestDto;
import com.project.skilled_project.domain.columns.entity.Columns;
import com.project.skilled_project.domain.columns.repository.ColumnsRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
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

    // 기능
    // 받아온 컬럼 id 2개로 현재 컬럼, 목표 컬럼값 받아오기.
    Columns nowColumns = findColumns(columnsId);
    Columns targetColumns = findColumns(columnsChangeNumberRequestDto.getColumnsId());
    // 받아온 두 컬럼이 보드가 다르면 던지기
    if (!Objects.equals(nowColumns.getBoardId(), targetColumns.getBoardId())) {
      throw new RuntimeException("두 컬럼의 보드가 다르면 이동이 불가능 합니다.");
    }
    // 이동할 목표의 바로 직전 컬럼의 포지션 값을 받아옴
    Long centerPosition = getCenterPosition(nowColumns.getPosition(), targetColumns.getPosition(),
        nowColumns.getBoardId());
    // 만약 해당 포지션값이 현재 포지션과 같으면
    // 현상유지 이므로 같지 않을때만 작업 없으면 작업 종료

    // 새로 들어갈 포지션 값을 만들어줌
    long newPosition = (targetColumns.getPosition() + centerPosition) / 2;
    // 새로운 포지션값 적용
    nowColumns.changePositionColumns(newPosition);
    // 만약 새로운 포지션 값 ~ 타겟 포지션값의 사이가 얼마 안남았으면
    if ((newPosition < targetColumns.getPosition()
        && newPosition + 100L > targetColumns.getPosition())
        || (newPosition > targetColumns.getPosition()
        && newPosition < targetColumns.getPosition() + 100L)) {
      // 모든 컬럼들의 포지션값을 새로 잡아주자.
      repositioning(nowColumns.getBoardId());
    }
  }


  public void repositioning(Long boardId) {
    // 먼저 해당 보드의 모든 값을 가져오자
    // 해당 보드의 모든 컬럼값을 리스트로 가져오자
    List<Columns> allColumns = columnsRepository.getAllColumns(boardId);
    for (int i = 0; i < allColumns.size(); i++) {
      allColumns.get(i).changePositionColumns((i + 1) * 1024L);
    }
  }

  public Long getCenterPosition(Long nowPosition, Long targetPosition, Long boardId) {
    if (nowPosition > targetPosition) {
      return columnsRepository.getCenterColumnsPositionNowBig(boardId,
          nowPosition, targetPosition);
    } else {
      return columnsRepository.getCenterColumnsPositionTargetBig(
          boardId,
          nowPosition, targetPosition);
    }
  }

  @Override
  public Columns findColumns(Long columnsId) {

    return columnsRepository.findById(columnsId).orElseThrow(
        () -> new EntityNotFoundException("컬럼 없음")
    );
  }
}


