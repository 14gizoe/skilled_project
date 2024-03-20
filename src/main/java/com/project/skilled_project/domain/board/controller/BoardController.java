package com.project.skilled_project.domain.board.controller;

import com.project.skilled_project.domain.board.dto.request.BoardRequestDto;
import com.project.skilled_project.domain.board.dto.request.UserInviteRequestDto;
import com.project.skilled_project.domain.board.dto.response.BoardDto;
import com.project.skilled_project.domain.board.dto.response.BoardsResponseDto;
import com.project.skilled_project.domain.board.service.BoardService;
import com.project.skilled_project.global.response.CommonResponse;
import com.project.skilled_project.global.util.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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

  @PostMapping
  public void createBoard(
      @RequestBody BoardRequestDto req,
      UserDetails userDetails
  ) {
    boardService.createBoard(req, userDetails.getUsername());
  }

  @GetMapping
  public ResponseEntity<CommonResponse<BoardsResponseDto>> getBoards(
      UserDetails userDetails
  ) {
    return ResponseEntity.ok().body(
        CommonResponse.<BoardsResponseDto>builder()
            .data(boardService.getBoards(userDetails.getUsername()))
            .build()
    );
  }

  @GetMapping("/{boardId}")
  public ResponseEntity<CommonResponse<BoardDto>> getBoard(
      @PathVariable Long boardId,
      UserDetails userDetails
  ) {
    return ResponseEntity.ok().body(
        CommonResponse.<BoardDto>builder()
            .data(boardService.getBoard(boardId, userDetails.getUsername()))
            .build()
    );
  }

  @PutMapping("/{boardId}")
  public void updateBoard(
      @PathVariable Long boardId,
      @RequestBody BoardRequestDto req,
      UserDetails userDetails
  ) {
    boardService.updateBoard(boardId, req, userDetails.getUsername());
  }

  @PutMapping("/{boardId}/invite")
  public void inviteUser(
      @PathVariable Long boardId,
      @RequestBody UserInviteRequestDto req,
      UserDetails userDetails
  ) {
    boardService.inviteUser(boardId, req, userDetails.getUsername());
  }

  @DeleteMapping("/{boardId}")
  public void deleteBoard(
      @PathVariable Long boardId,
      UserDetails userDetails
  ) {
    boardService.deleteBoard(boardId, userDetails.getUsername());
  }
}
