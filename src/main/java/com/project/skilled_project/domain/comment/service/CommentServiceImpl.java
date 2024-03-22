package com.project.skilled_project.domain.comment.service;

import com.project.skilled_project.domain.card.entity.Card;
import com.project.skilled_project.domain.card.service.CardService;
import com.project.skilled_project.domain.comment.entity.Comment;
import com.project.skilled_project.domain.comment.repository.CommentRepository;
import com.project.skilled_project.domain.comment.repository.CommentRepositoryQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final CommentRepositoryQuery commentRepositoryQuery;
  private final CardService cardService;

  @Override
  @Transactional
  public void createComment(String content, Long cardId, Long userId) {
    Card card = cardService.findCardById(cardId);
    Comment comment = new Comment(content, card.getId(), userId);
    commentRepository.save(comment);
    cardService.commentCountUp(cardId);

  }

  @Override
  @Transactional
  public void updateComment(Long commentId, String content, Long userId) {
    Comment comment = commentRepositoryQuery.getComment(commentId, userId);

    comment.update(content);
  }

  @Override
  @Transactional
  public void deleteComment(Long commentId, Long userId) {
    Comment comment = commentRepositoryQuery.getComment(commentId, userId);
    comment.delete();
    cardService.commentCountDown(comment.getCardId());
  }
}
