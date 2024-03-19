package com.project.skilled_project.domain.columns.controller;

import com.project.skilled_project.domain.columns.dto.request.ColumnsCreateRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsUpdateNameRequestDto;
import com.project.skilled_project.domain.columns.service.ColumnsService;
import com.project.skilled_project.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ColumnsController {

  private final ColumnsService columnsService;

  // 컬럼 생성 /api/columns
  @PostMapping
  public ResponseEntity<CommonResponse<Void>> createColumns(
      @RequestBody ColumnsCreateRequestDto columnsCreateRequestDto,
      @PathVariable Long boardId
  ) {
    columnsService.createColumns(columnsCreateRequestDto, boardId);
    return  ResponseEntity.status(HttpStatus.OK.value()).body(
        CommonResponse.<Void>builder().build());
  }

  // 컬럼 이름 수정 /api/columns/{columnId}
  @PutMapping("/{columnId}")
  public ResponseEntity<CommonResponse<Void>> updateNameColumns(
      @PathVariable Long columnId,
      @RequestBody ColumnsUpdateNameRequestDto columnsUpdateNameRequestDto
  ) {
    columnsService.updateNameColumn(columnId, columnsUpdateNameRequestDto);
    return ResponseEntity.status(HttpStatus.OK.value()).body(
        CommonResponse.<Void>builder().build());
  }

  // 컬럼 삭제 /api/columns/{columnId}

  // 컬럼 순서 이동 /api/columns/{columnId}

}
