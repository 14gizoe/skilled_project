package com.project.skilled_project.domain.board.repository;

import com.project.skilled_project.domain.board.dto.response.BoardResponseDto;
import com.project.skilled_project.domain.board.dto.response.BoardsDto;
import java.util.List;

public interface BoardRepositoryCustom {

  List<BoardsDto> getBoards();

  List<BoardResponseDto> getBoard(Long boardId);
}
