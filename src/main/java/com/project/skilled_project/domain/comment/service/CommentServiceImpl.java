package com.project.skilled_project.domain.comment.service;

import com.project.skilled_project.domain.comment.entity.Comment;
import com.project.skilled_project.domain.comment.repository.CommentRepository;
import com.project.skilled_project.domain.comment.repository.CommentRepositoryQuery;
import com.project.skilled_project.domain.user.entity.User;
import com.project.skilled_project.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final CommentRepositoryQuery commentRepositoryQuery;

  @Override
  @Transactional
  public void createComment(String content, Long cardId, Long userId) {
    // cardId 검증
    Comment comment = new Comment(content, cardId, userId);
    commentRepository.save(comment);
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
  }
}
