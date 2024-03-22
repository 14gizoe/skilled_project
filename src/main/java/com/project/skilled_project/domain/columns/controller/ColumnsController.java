package com.project.skilled_project.domain.columns.controller;

import com.project.skilled_project.domain.columns.dto.request.ColumnsChangeNumberRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsCreateRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsUpdateNameRequestDto;
import com.project.skilled_project.domain.columns.service.ColumnsService;
import com.project.skilled_project.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/columns")
public class ColumnsController {

  private final ColumnsService columnsService;

  // 컬럼 생성 /api/columns
  @PostMapping
  public void createColumns(
      @RequestBody ColumnsCreateRequestDto columnsCreateRequestDto
  ) {
    columnsService.createColumns(columnsCreateRequestDto);
  }

  // 컬럼 이름 수정 /api/columns/{columnId}
  @PutMapping("/{columnsId}")
  public void updateNameColumns(
      @PathVariable Long columnsId,
      @RequestBody ColumnsUpdateNameRequestDto columnsUpdateNameRequestDto
  ) {
    columnsService.updateNameColumns(columnsId, columnsUpdateNameRequestDto);
  }

  // 컬럼 삭제 /api/columns/{columnId}
  @DeleteMapping("/{columnsId}")
  public void deleteColumns(
      @PathVariable Long columnsId) {

    columnsService.deleteColumns(columnsId);
  }

  //컬럼 순서 이동 /api/columns/{columnId}
  @PutMapping("/{columnsId}/shifting")
  public void changeNumberColumns(
      @PathVariable Long columnsId,
      @RequestBody ColumnsChangeNumberRequestDto columnsChangeNumberRequestDto
  ) {
    columnsService.changeNumberColumns(columnsId, columnsChangeNumberRequestDto);
  }

}
