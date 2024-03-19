package com.project.skilled_project.domain.board.service;

import com.project.skilled_project.domain.board.dto.request.BoardRequestDto;
import com.project.skilled_project.domain.board.dto.request.UserInviteRequestDto;
import com.project.skilled_project.domain.board.dto.response.BoardDto;
import com.project.skilled_project.domain.board.dto.response.BoardResponseDto;
import com.project.skilled_project.domain.board.dto.response.BoardsDto;
import com.project.skilled_project.domain.board.dto.response.BoardsResponseDto;
import com.project.skilled_project.domain.board.dto.response.CardDto;
import com.project.skilled_project.domain.board.dto.response.ColumDto;
import com.project.skilled_project.domain.board.entity.Board;
import com.project.skilled_project.domain.board.repository.BoardRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j(topic = "BoardService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

  private final BoardRepository boardRepository;

  @Override
  @Transactional
  public void createBoard(BoardRequestDto req) {
    Board board = new Board(req);
    boardRepository.save(board);
  }

  @Override
  public BoardsResponseDto getBoards() {
    List<BoardsDto> boardsList = boardRepository.getBoards();
    BoardsResponseDto boardsResponseDto = new BoardsResponseDto();
    for (BoardsDto boardsDto : boardsList) {
      boardsResponseDto.boardTitleUpdate(boardsDto.getTitle());
    }
    return boardsResponseDto;
  }

  @Override
  public BoardDto getBoard(Long boardId) {
    List<BoardResponseDto> boardList = boardRepository.getBoard(boardId);
    String title = boardList.get(0).getBoardTitle();
    String color = boardList.get(0).getBoardColor();
    Map<Long, ColumDto> columDtoMap = new LinkedHashMap<>();
    Map<Long, CardDto> cardDtoMap = new LinkedHashMap<>();
    for (BoardResponseDto dto : boardList) {
      if (dto.getCard() != null && dto.getCard().getColumnId().equals(dto.getColumns().getId())) {
        cardDtoMap.put(
            dto.getCard().getColumnId(),
            new CardDto(dto.getCard())
        );
      }
    }
    System.out.println("cardDtoMap = " + cardDtoMap);
    List<CardDto> cardDtos = new ArrayList<>();
    for (BoardResponseDto dto : boardList) {
      if (dto.getColumns() != null) {
        columDtoMap.put(
            dto.getColumns().getId(),
            new ColumDto(
                dto.getColumns().getTitle(),
                cardDtos
            )
        );
      }
    }

    List<ColumDto> columDtoList = new ArrayList<>(columDtoMap.values());
    return new BoardDto(title, color, columDtoList);
  }

  @Override
  @Transactional
  public void updateBoard(Long boardId, BoardRequestDto req) {
    Board board = validateBoard(boardId);
    board.update(req);
  }

  @Override
  @Transactional
  public void inviteUser(Long boardId, UserInviteRequestDto req) {

  }

  @Override
  @Transactional
  public void deleteBoard(Long boardId) {
    Board board = validateBoard(boardId);
    board.softDelete();
  }

  private Board validateBoard(Long boardId) {
    return boardRepository.findById(boardId).orElseThrow(
        () -> new NullPointerException("해당 보드가 존재하지 않습니다.")
    );
  }
}
