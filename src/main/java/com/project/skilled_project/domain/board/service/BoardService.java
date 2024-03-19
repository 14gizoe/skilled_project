package com.project.skilled_project.domain.board.service;

import com.project.skilled_project.domain.board.dto.request.BoardRequestDto;
import com.project.skilled_project.domain.board.dto.request.UserInviteRequestDto;
import com.project.skilled_project.domain.board.dto.response.BoardDto;
import com.project.skilled_project.domain.board.dto.response.BoardsResponseDto;


public interface BoardService {

  void createBoard(BoardRequestDto req);

  BoardsResponseDto getBoards();

  BoardDto getBoard(Long boardId);

  void updateBoard(Long boardId, BoardRequestDto req);

  void inviteUser(Long boardId, UserInviteRequestDto req);

  void deleteBoard(Long boardId);
}
