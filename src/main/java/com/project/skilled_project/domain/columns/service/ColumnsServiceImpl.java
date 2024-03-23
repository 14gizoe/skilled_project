package com.project.skilled_project.domain.columns.service;

import com.project.skilled_project.domain.columns.dto.request.ColumnsChangeNumberRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsCreateRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsUpdateNameRequestDto;
import com.project.skilled_project.domain.columns.entity.Columns;
import com.project.skilled_project.domain.columns.repository.ColumnsRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ColumnsServiceImpl implements ColumnsService {

  private final ColumnsRepository columnsRepository;
  private final RedissonClient redissonClient;

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
    String lockName = "COLUMNS" + columnsId;
    RLock rLock = redissonClient.getLock(lockName);
    try {
      boolean available = rLock.tryLock(15L, 3L, TimeUnit.SECONDS);
      if (!available) {
        throw new RuntimeException();
      }
      Columns nowColumns = findColumns(columnsId);
      Columns targetColumns = findColumns(columnsChangeNumberRequestDto.getColumnsId());
      if (!Objects.equals(nowColumns.getBoardId(), targetColumns.getBoardId())) {
        throw new RuntimeException();
      }
      System.out.println(nowColumns.getPosition());
      System.out.println(targetColumns.getPosition());
      Long centerPosition = 0L;
      if (nowColumns.getPosition() > targetColumns.getPosition()) {
        centerPosition = columnsRepository.getCenterColumnsPositionNowBig(nowColumns.getBoardId(),
            nowColumns.getPosition(), targetColumns.getPosition());
      } else {
        centerPosition = columnsRepository.getCenterColumnsPositionTargetBig(
            nowColumns.getBoardId(),
            nowColumns.getPosition(), targetColumns.getPosition());
      }
      System.out.println(nowColumns.getPosition());
      System.out.println(centerPosition);
      System.out.println(targetColumns.getPosition());
      if (nowColumns.getPosition() != centerPosition) {
        Long newPosition = (targetColumns.getPosition() + centerPosition) / 2;
        nowColumns.changePositionColumns(newPosition);
      }

    } catch (InterruptedException e) {
      //락을 얻으려고 시도하다가 인터럽트를 받았을 때 발생하는 예외
      throw new RuntimeException();
    } finally {
      rLock.unlock();
    }
  }


  @Override
  public Columns findColumns(Long columnsId) {

    return columnsRepository.findById(columnsId).orElseThrow(
        () -> new EntityNotFoundException("컬럼 없음")
    );
  }
}


