package com.project.skilled_project.domain.board.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.project.skilled_project.domain.board.dto.request.BoardRequestDto;
import com.project.skilled_project.domain.board.dto.request.UserInviteRequestDto;
import com.project.skilled_project.domain.board.dto.response.BoardDto;
import com.project.skilled_project.domain.board.dto.response.BoardResponseDto;
import com.project.skilled_project.domain.board.dto.response.BoardsDto;
import com.project.skilled_project.domain.board.dto.response.BoardsResponseDto;
import com.project.skilled_project.domain.board.dto.response.CardDto;
import com.project.skilled_project.domain.board.dto.response.ColumnDto;
import com.project.skilled_project.domain.board.entity.Board;
import com.project.skilled_project.domain.board.entity.Participant;
import com.project.skilled_project.domain.board.repository.BoardRepository;
import com.project.skilled_project.domain.board.repository.ParticipantRepository;
import com.project.skilled_project.domain.user.entity.User;
import com.project.skilled_project.domain.user.service.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
  private final ParticipantRepository participantRepository;
  private final UserService userService;

  @Override
  @Transactional
  public void createBoard(BoardRequestDto req, String username) {
    User user = userService.findUser(username);
    Board board = new Board(req, user);
    Board savedBoard = boardRepository.save(board);
    Participant participant = new Participant(savedBoard.getId(), user.getId());
    participantRepository.save(participant);
  }

  @Override
  public BoardsResponseDto getBoards(String username) {
    User user = userService.findUser(username);
    validateUserByUser(user);
    List<BoardsDto> boardsList = boardRepository.getBoards();
    BoardsResponseDto boardsResponseDto = new BoardsResponseDto();
    for (BoardsDto boardsDto : boardsList) {
      boardsResponseDto.boardTitleUpdate(boardsDto.getTitle());
    }
    return boardsResponseDto;
  }

  private void validateUserByUser(User user) {
    List<Long> users = participantRepository.findByUserId(user.getId());
    if (users.isEmpty()) {
      throw new NullPointerException("보드에 존재하지 않은 유저입니다.");
    }
  }

  @Override
  public BoardDto getBoard(Long boardId, String username) {
    User user = userService.findUser(username);
    validateUser(user, boardId);
    List<BoardResponseDto> boardList = boardRepository.getBoard(boardId);

    BoardResponseDto firstBoard = boardList.stream()
        .findFirst()
        .orElseThrow(() -> new NotFoundException("Board not found"));
    String title = firstBoard.getBoardTitle();
    String color = firstBoard.getBoardColor();
    List<ColumnDto> columnDtoList = mapppingBoard(boardList);
    return new BoardDto(title, color, columnDtoList);
  }

  private List<ColumnDto> mapppingBoard(List<BoardResponseDto> boardList) {
    Map<Long, ColumnDto> columnDtoMap = new HashMap<>();
    Map<Long, List<CardDto>> cardDtoMap = new HashMap<>();

    for (BoardResponseDto dto : boardList) {
      if (dto.getColumns() != null) {
        Long columnId = dto.getColumns().getColumnsId();
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
    }

    for (Map.Entry<Long, ColumnDto> entry : columnDtoMap.entrySet()) {
      Long columnId = entry.getKey();
      List<CardDto> cards = cardDtoMap.get(columnId);
      entry.getValue().setCards(cards);
    }

    return new ArrayList<>(columnDtoMap.values());
  }

  @Override
  @Transactional
  public void updateBoard(Long boardId, BoardRequestDto req, String username) {
    Board board = validateBoard(boardId);
    User user = userService.findUser(username);
    validateUser(user, boardId);
    board.update(req);
  }

  @Override
  @Transactional
  public void inviteUser(Long boardId, UserInviteRequestDto req, String username) {
    User user = userService.findUser(username);
    validateUser(user, boardId);
    List<Long> invitedUser = participantRepository.findAllByBoardId(boardId);
    Set<Long> mergedSet = new HashSet<>(req.getInvitingList());
    mergedSet.addAll(invitedUser);

    for (Long userId : mergedSet) {
      if (!invitedUser.contains(userId)) {
        Participant participant = new Participant(boardId, userId);
        participantRepository.save(participant);
      }
    }
  }

  @Override
  @Transactional
  public void deleteUser(Long boardId, UserInviteRequestDto req, String username) {
    User user = userService.findUser(username);
    validateUser(user, boardId);

    List<Long> deletingUserIds = req.getInvitingList();

    // 한 번의 쿼리로 여러 사용자 삭제
    participantRepository.deleteByBoardIdAndUserIdIn(boardId, deletingUserIds);
  }

  @Override
  @Transactional
  public void deleteBoard(Long boardId, String username) {
    Board board = validateBoard(boardId);
    User user = userService.findUser(username);
    validateUser(user, boardId);
    board.softDelete();
  }

  private void validateUser(User user, Long boardId) {
    List<Long> userList = participantRepository.findAllByBoardId(boardId);
    if (!userList.contains(user.getId())) {
      throw new IllegalStateException("보드 권한이 있는 유저가 아닙니다.");
    }
  }

  private Board validateBoard(Long boardId) {
    return boardRepository.findById(boardId).orElseThrow(
        () -> new NullPointerException("해당 보드가 존재하지 않습니다.")
    );
  }
}
