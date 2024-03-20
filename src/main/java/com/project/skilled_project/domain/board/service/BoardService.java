package com.project.skilled_project.domain.board.service;

import com.project.skilled_project.domain.board.dto.request.BoardRequestDto;
import com.project.skilled_project.domain.board.dto.request.UserInviteRequestDto;
import com.project.skilled_project.domain.board.dto.response.BoardDto;
import com.project.skilled_project.domain.board.dto.response.BoardsResponseDto;


public interface BoardService {

  void createBoard(BoardRequestDto req, String user);

  BoardsResponseDto getBoards(String username);

  BoardDto getBoard(Long boardId, String user);

  void updateBoard(Long boardId, BoardRequestDto req, String user);

  void inviteUser(Long boardId, UserInviteRequestDto req, String username);

  void deleteBoard(Long boardId, String user);
}
