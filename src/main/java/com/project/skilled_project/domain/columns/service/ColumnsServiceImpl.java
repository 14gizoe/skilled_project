package com.project.skilled_project.domain.columns.service;

import com.project.skilled_project.domain.columns.dto.request.ColumnsChangeNumberRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsCreateRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsUpdateNameRequestDto;
import com.project.skilled_project.domain.columns.entity.Columns;
import com.project.skilled_project.domain.columns.repository.ColumnsRepository;
import java.util.Arrays;
import java.util.Collections;
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
    List<Columns> columnsList = columnsRepository.findAllByBoardId(columns.getBoardId());
    Long[] columnsIdArrays = new Long[columnsList.size()];
    Long[] columnsPosition = new Long[columnsList.size()];
    for (int i = 0; i < columnsPosition.length; i++) {
      columnsIdArrays[i] = columnsList.get(i).getColumnsId();
      columnsPosition[i] = columnsList.get(i).getPosition();
    }
    // 포지션에 맞춰서 정렬
    for (int i = 0; i < columnsPosition.length; i++) {
      for (int j = i + 1; j < columnsPosition.length; j++) {
        if (columnsPosition[i] > columnsPosition[j]) {
          Long tmp = columnsPosition[i];
          columnsPosition[i] = columnsPosition[j];
          columnsPosition[j] = tmp;
          tmp = columnsIdArrays[i];
          columnsIdArrays[i] = columnsIdArrays[j];
          columnsIdArrays[j] = tmp;
        }
      }
    }
    Long[] columnsIdAdressCopy = columnsIdArrays.clone();
    // 누가 누구의 자리를 제치고 갈건지.
    // 내려갈땐 대상의 아래까지
    // 올라갈땐 대상의 바로 위까지.
    Long who = columns.getPosition();
    Long where = columns1.getPosition();
    if (who > where) {
      // 옮기려는 컬럼이 더 아래에 있을때 (위로 올릴때 )
      List<Long> columnsPositionList = Arrays.asList(columnsPosition);
      Collections.reverse(columnsPositionList);
      columnsPosition = columnsPositionList.toArray(columnsPosition);

      List<Long> columnsIdArraysList = Arrays.asList(columnsIdArrays);
      Collections.reverse(columnsIdArraysList);
      columnsIdArrays = columnsIdArraysList.toArray(columnsIdArrays);

      List<Long> columnsIdAdressCopyList = Arrays.asList(columnsIdAdressCopy);
      Collections.reverse(columnsIdAdressCopyList);
      columnsIdAdressCopy = columnsIdAdressCopyList.toArray(columnsIdAdressCopy);
    }
    // 옮기려는 컬럼이 더 위에 있을때는 그냥 ( 아래로 내릴때 )

    for (int i = 0; i < columnsPosition.length; i++) {
      if (Objects.equals(who, columnsPosition[i])) {
        for (int j = i + 1; j < columnsPosition.length; j++) {
          Long tmp = columnsIdArrays[j - 1];
          columnsIdArrays[j - 1] = columnsIdArrays[j];
          columnsIdArrays[j] = tmp;
          if (Objects.equals(columnsPosition[j], where)) {
            break;
          }
        }
        break;
      }
    }
    for (int i = 0; i < columnsIdArrays.length; i++) {
      if (!Objects.equals(columnsIdArrays[i], columnsIdAdressCopy[i])) {
        for (int j = i; j < columnsIdArrays.length; j++) {
          if (Objects.equals(columnsIdArrays[j], columnsIdAdressCopy[j])) {
            break;
          }
          System.out.println(Arrays.toString(columnsIdArrays));
          System.out.println(columnsIdArrays[j]);
          // 값 바꿔주기.
          Columns columnsChange = columnsRepository.findById(columnsIdArrays[j])
              .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 컬럼입니다.2"));
          columnsChange.changePositionColumns(columnsPosition[j]);
        }
        break;
      }
    }
  }
}
