package com.project.skilled_project.domain.card.service;


import com.project.skilled_project.domain.board.entity.Board;
import com.project.skilled_project.domain.board.repository.BoardRepository;
import com.project.skilled_project.domain.card.dto.request.CardCreateRequestDto;
import com.project.skilled_project.domain.card.entity.Card;
import com.project.skilled_project.domain.card.repository.CardRepository;
import com.project.skilled_project.domain.columns.entity.Columns;
import com.project.skilled_project.domain.columns.repository.ColumnsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{
  private final CardRepository cardRepository;
  private final ColumnsRepository columnsRepository;
  private final BoardRepository boardRepository;
  @Override
  public void createCard(CardCreateRequestDto cardCreateRequestDto) {
    Columns columns = columnsRepository.findById(cardCreateRequestDto.getColumnId()).orElseThrow(
        () -> new IllegalArgumentException("컬럼 없음")
    );
    Board board = boardRepository.findById(cardCreateRequestDto.getBoardId()).orElseThrow(
        () -> new IllegalArgumentException("보드 없음")
    );
    Card card = new Card(cardCreateRequestDto);
    cardRepository.save(card);
  }
}
