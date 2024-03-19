package com.project.skilled_project.domain.board.service;

import com.project.skilled_project.domain.board.dto.request.BoardRequestDto;
import com.project.skilled_project.domain.board.dto.request.UserInviteRequestDto;
import com.project.skilled_project.domain.board.dto.response.BoardDto;
import com.project.skilled_project.domain.board.dto.response.BoardResponseDto;
import com.project.skilled_project.domain.board.dto.response.BoardsDto;
import com.project.skilled_project.domain.board.dto.response.BoardsResponseDto;
import com.project.skilled_project.domain.board.dto.response.CardDto;
import com.project.skilled_project.domain.board.dto.response.ColumnDto;
import com.project.skilled_project.domain.board.entity.Board;
import com.project.skilled_project.domain.board.repository.BoardRepository;
import java.util.ArrayList;
import java.util.HashMap;
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

    List<ColumnDto> columnDtoList = mapppingBoard(
        boardList);
    return new BoardDto(title, color, columnDtoList);
  }

  private List<ColumnDto> mapppingBoard(List<BoardResponseDto> boardList) {
    Map<Long, ColumnDto> columnDtoMap = new HashMap<>();
    Map<Long, List<CardDto>> cardDtoMap = new HashMap<>();

    for (BoardResponseDto dto : boardList) {
      Long columnId = dto.getColumns().getId();
      if (!columnDtoMap.containsKey(columnId)) {
        columnDtoMap.put(columnId, new ColumnDto(dto.getColumns().getTitle(), new ArrayList<>()));
        cardDtoMap.put(columnId, new ArrayList<>());
      }

      if (dto.getCard() != null) {
        cardDtoMap.get(columnId).add(new CardDto(
            dto.getCard()
        ));
      }
    }

    for (Map.Entry<Long, ColumnDto> entry : columnDtoMap.entrySet()) {
      Long columnId = entry.getKey();
      List<CardDto> cards = cardDtoMap.get(columnId);
      entry.getValue().setCards(cards);
    }

    List<ColumnDto> columnDtoList = new ArrayList<>(columnDtoMap.values());
    return columnDtoList;
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
