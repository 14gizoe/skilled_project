package com.project.skilled_project.domain.comment.service;


public interface CommentService {

  void createComment(String content, Long cardId, Long userId);

  void updateComment(Long commentId, String content, Long userId);

  void deleteComment(Long commentId, Long userId);
}
