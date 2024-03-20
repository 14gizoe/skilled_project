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
  private final UserService userService;

  @Override
  @Transactional
  public void createComment(String content, Long cardId, String verifiedUser) {
    // cardId 검증
    User user = userService.findUser(verifiedUser);
    Comment comment = new Comment(content, cardId, user.getId());
    commentRepository.save(comment);
  }

  @Override
  @Transactional
  public void updateComment(Long commentId, String content, String username) {
    User user = userService.findUser(username);
    Comment comment = commentRepositoryQuery.getComment(commentId, user.getId());

    comment.update(content);
  }
}
