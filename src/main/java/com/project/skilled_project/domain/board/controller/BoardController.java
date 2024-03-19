package com.project.skilled_project.domain.board.controller;

import com.project.skilled_project.domain.board.dto.request.BoardRequestDto;
import com.project.skilled_project.domain.board.dto.request.UserInviteRequestDto;
import com.project.skilled_project.domain.board.dto.response.BoardDto;
import com.project.skilled_project.domain.board.dto.response.BoardResponseDto;
import com.project.skilled_project.domain.board.dto.response.BoardsResponseDto;
import com.project.skilled_project.domain.board.service.BoardService;
import com.project.skilled_project.global.response.CommonResponse;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/boards")
public class BoardController {

  private final BoardService boardService;
  @PostMapping("")
  public ResponseEntity<CommonResponse<Void>> createBoard(
      @RequestBody BoardRequestDto req
  ){
    boardService.createBoard(req);
    return ResponseEntity.ok().body(
        CommonResponse.<Void>builder().build()
    );
  }

  @GetMapping("")
  public ResponseEntity<CommonResponse<BoardsResponseDto>> getBoards(){
    return ResponseEntity.ok().body(
        CommonResponse.<BoardsResponseDto>builder()
            .data(boardService.getBoards())
            .build()
    );
  }

  @GetMapping("/{boardId}")
  public ResponseEntity<CommonResponse<BoardDto>> getBoard(
      @PathVariable Long boardId
  ){
    return ResponseEntity.ok().body(
        CommonResponse.<BoardDto>builder()
            .data(boardService.getBoard(boardId))
            .build()
    );
  }

  @PutMapping("/{boardId}")
  public ResponseEntity<CommonResponse<Void>> updateBoard(
      @PathVariable Long boardId,
      @RequestBody BoardRequestDto req
  ){
    boardService.updateBoard(boardId,req);
    return ResponseEntity.ok().body(
        CommonResponse.<Void>builder().build()
    );
  }

  @PutMapping("/{boardId}/invite")
  public ResponseEntity<CommonResponse<Void>> inviteUser(
      @PathVariable Long boardId,
      @RequestBody UserInviteRequestDto req
  ){
    boardService.inviteUser(boardId, req);
    return ResponseEntity.ok().body(
        CommonResponse.<Void>builder().build()
    );
  }

  @DeleteMapping("/{boardId}")
  public ResponseEntity<CommonResponse<Void>> deleteBoard(
      @PathVariable Long boardId
  ){
    boardService.deleteBoard(boardId);
    return ResponseEntity.ok().body(
        CommonResponse.<Void>builder().build()
    );
  }
}
