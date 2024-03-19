package com.project.skilled_project.domain.board.service;

import com.project.skilled_project.domain.board.dto.request.BoardRequestDto;
import com.project.skilled_project.domain.board.dto.request.UserInviteRequestDto;
import com.project.skilled_project.domain.board.dto.response.BoardDto;
import com.project.skilled_project.domain.board.dto.response.BoardsResponseDto;
import com.project.skilled_project.domain.user.entity.User;


public interface BoardService {

  void createBoard(BoardRequestDto req, User user);

  BoardsResponseDto getBoards();

  BoardDto getBoard(Long boardId, User user);

  void updateBoard(Long boardId, BoardRequestDto req, User user);

  void inviteUser(Long boardId, UserInviteRequestDto req);

  void deleteBoard(Long boardId, User user);
}
