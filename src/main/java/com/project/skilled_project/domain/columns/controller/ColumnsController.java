package com.project.skilled_project.domain.columns.controller;

import com.project.skilled_project.domain.columns.dto.request.ColumnsChangeNumberRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsCreateRequestDto;
import com.project.skilled_project.domain.columns.dto.request.ColumnsUpdateNameRequestDto;
import com.project.skilled_project.domain.columns.dto.response.ColumnResponseDto;
import com.project.skilled_project.domain.columns.service.ColumnsService;
import com.project.skilled_project.global.response.CommonResponse;
import com.project.skilled_project.global.util.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
  public ResponseEntity<CommonResponse<Void>> createColumns(
      @RequestBody ColumnsCreateRequestDto columnsCreateRequestDto
  ) {
    columnsService.createColumns(columnsCreateRequestDto);
    return ResponseEntity.status(HttpStatus.OK.value()).body(
        CommonResponse.<Void>builder().build());
  }

  // 컬럼 이름 수정 /api/columns/{columnId}
  @PutMapping("/{columnsId}")
  public ResponseEntity<CommonResponse<Void>> updateNameColumns(
      @PathVariable Long columnsId,
      @RequestBody ColumnsUpdateNameRequestDto columnsUpdateNameRequestDto
  ) {
    columnsService.updateNameColumns(columnsId, columnsUpdateNameRequestDto);
    return ResponseEntity.status(HttpStatus.OK.value()).body(
        CommonResponse.<Void>builder().build());
  }

  // 컬럼 삭제 /api/columns/{columnId}
  @DeleteMapping("/{columnsId}")
  public ResponseEntity<CommonResponse<Void>> deleteColumns(
      @PathVariable Long columnsId) {

    columnsService.deleteColumns(columnsId);
    return ResponseEntity.status(HttpStatus.OK.value()).body(
        CommonResponse.<Void>builder().build());
  }

  //컬럼 순서 이동 /api/columns/{columnId}
  @PutMapping("/{columnsId}/shifting")
  public ResponseEntity<CommonResponse<Void>> changeNumberColumns(
      @PathVariable Long columnsId,
      @RequestBody ColumnsChangeNumberRequestDto columnsChangeNumberRequestDto
  ) {
    columnsService.changeNumberColumns(columnsId, columnsChangeNumberRequestDto);
    return ResponseEntity.status(HttpStatus.OK.value()).body(
        CommonResponse.<Void>builder().build());
  }

  @GetMapping
  public ResponseEntity<CommonResponse<List<ColumnResponseDto>>> getColumns() {
    List<ColumnResponseDto> data = columnsService.getColumns();
    return CommonResponse.ok(data);
  }
}
