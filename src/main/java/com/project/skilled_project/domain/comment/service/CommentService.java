package com.project.skilled_project.domain.comment.service;


public interface CommentService {

  void createComment(String content, Long cardId, String verifiedUser);

  void updateComment(Long commentId, String content, String username);

  void deleteComment(Long commentId, String username);
}
