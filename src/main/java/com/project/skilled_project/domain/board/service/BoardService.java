package com.project.skilled_project.domain.board.service;

import com.project.skilled_project.domain.board.dto.request.BoardRequestDto;
import com.project.skilled_project.domain.board.dto.request.UserInviteRequestDto;
import com.project.skilled_project.domain.board.dto.response.BoardDto;
import com.project.skilled_project.domain.board.dto.response.BoardsResponseDto;
import com.project.skilled_project.domain.board.entity.Board;
import com.project.skilled_project.domain.user.entity.User;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;


public interface BoardService {

  void createBoard(BoardRequestDto req, User user);

  BoardsResponseDto getBoards(User username);

  BoardDto getBoard(Long boardId, User user) throws NotFoundException;

  Board findBoard(Long boardId);

  void updateBoard(Long boardId, BoardRequestDto req, User user);

  void inviteUser(Long boardId, UserInviteRequestDto req, User username);

  void deleteBoard(Long boardId, User user);

  void deleteUser(Long boardId, UserInviteRequestDto req, User username);
}
