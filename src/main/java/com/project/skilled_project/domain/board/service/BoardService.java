package com.project.skilled_project.domain.board.service;

import com.project.skilled_project.domain.board.dto.response.BoardResponseDto;
import com.project.skilled_project.domain.board.dto.response.BoardsResponseDto;


public interface BoardService {

  void createBoard();

  BoardsResponseDto getBoards();

  BoardResponseDto getBoard();

  void updateBoard();

  void inviteUser();

  void deleteBoard();
}
