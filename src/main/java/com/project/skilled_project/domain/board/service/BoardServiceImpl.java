package com.project.skilled_project.domain.board.service;

import com.project.skilled_project.domain.board.dto.request.BoardRequestDto;
import com.project.skilled_project.domain.board.dto.request.UserInviteRequestDto;
import com.project.skilled_project.domain.board.dto.response.BoardDto;
import com.project.skilled_project.domain.board.dto.response.BoardResponseDto;
import com.project.skilled_project.domain.board.dto.response.BoardsResponseDto;
import com.project.skilled_project.domain.board.dto.response.ColumnDto;
import com.project.skilled_project.domain.board.entity.Board;
import com.project.skilled_project.domain.board.entity.Participant;
import com.project.skilled_project.domain.board.repository.BoardRepository;
import com.project.skilled_project.domain.board.repository.ParticipantRepository;
import com.project.skilled_project.domain.card.dto.response.CardResponseDto;
import com.project.skilled_project.domain.user.entity.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j(topic = "BoardService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

  private final BoardRepository boardRepository;
  private final ParticipantRepository participantRepository;
  private final RedisTemplate<String, String> redisTemplate;

  @Override
  @Transactional
  public void createBoard(BoardRequestDto req, User user) {
    Board board = new Board(req, user);
    Board savedBoard = boardRepository.save(board);
    Participant participant = new Participant(savedBoard.getId(), user.getId());
    participantRepository.save(participant);
    redisTemplate.opsForList().leftPush("boardTitles", req.getTitle());
  }

  @Override
  public BoardsResponseDto getBoards(User user) {
    validateUserByUser(user);
//    List<BoardsDto> boardsList = boardRepository.getBoards();
//    BoardsResponseDto boardsResponseDto = new BoardsResponseDto();
//    for (BoardsDto boardsDto : boardsList) {
//      boardsResponseDto.boardTitleUpdate(boardsDto.getTitle());
//    }
    List<String> boardList = redisTemplate.opsForList().range("boardTitles", 0, -1);
    return new BoardsResponseDto(boardList);
  }

  private void validateUserByUser(User user) {
    List<Long> users = participantRepository.findByUserId(user.getId());
    if (users.isEmpty()) {
      throw new NullPointerException("보드에 존재하지 않은 유저입니다.");
    }
  }

  @Override
  public BoardDto getBoard(Long boardId, User user) throws NotFoundException {
    validateUser(user, boardId);
    List<BoardResponseDto> boardList = boardRepository.getBoard(boardId);
    List<String> userList = participantRepository.getUsernames(boardId);
    BoardResponseDto firstBoard = boardList.stream()
        .findFirst()
        .orElseThrow(NotFoundException::new);
    String title = firstBoard.getBoardTitle();
    String color = firstBoard.getBoardColor();
    List<ColumnDto> columnDtoList = mapppingBoard(boardList);
    return new BoardDto(title, color, userList, columnDtoList);
  }

  @Override
  public Board findBoard(Long boardId) {
    return boardRepository.findById(boardId)
        .orElseThrow(() -> new NullPointerException("보드가 존재하지 않음"));
  }

  private List<ColumnDto> mapppingBoard(List<BoardResponseDto> boardList) {
    Map<Long, ColumnDto> columnDtoMap = new HashMap<>();
    Map<Long, List<CardResponseDto>> cardDtoMap = new HashMap<>();

    for (BoardResponseDto dto : boardList) {
      if (dto.getColumns() != null) {
        Long columnId = dto.getColumns().getColumnsId();
        if (!columnDtoMap.containsKey(columnId)) {
          columnDtoMap.put(columnId, new ColumnDto(dto.getColumns().getTitle(), new ArrayList<>()));
          cardDtoMap.put(columnId, new ArrayList<>());
        }

        if (dto.getCard() != null) {
          cardDtoMap.get(columnId).add(new CardResponseDto(
              dto.getCard()
          ));
        }
      }
    }

    for (Map.Entry<Long, ColumnDto> entry : columnDtoMap.entrySet()) {
      Long columnId = entry.getKey();
      List<CardResponseDto> cards = cardDtoMap.get(columnId);
      entry.getValue().setCards(cards);
    }

    return new ArrayList<>(columnDtoMap.values());
  }

  @Override
  @Transactional
  public void updateBoard(Long boardId, BoardRequestDto req, User user) {
    Board board = validateBoard(boardId);
    validateUser(user, boardId);
    board.update(req);
  }

  @Override
  @Transactional
  public void inviteUser(Long boardId, UserInviteRequestDto req, User user) {
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
  public void deleteUser(Long boardId, UserInviteRequestDto req, User user) {
    validateUser(user, boardId);

    List<Long> deletingUserIds = req.getInvitingList();

    // 한 번의 쿼리로 여러 사용자 삭제
    participantRepository.deleteByBoardIdAndUserIdIn(boardId, deletingUserIds);
  }

  @Override
  @Transactional
  public void deleteBoard(Long boardId, User user) {
    Board board = validateBoard(boardId);
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
