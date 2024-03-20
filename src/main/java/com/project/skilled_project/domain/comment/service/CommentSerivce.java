package com.project.skilled_project.domain.comment.service;


public interface CommentSerivce {

  void createComment(String content, Long cardId, String verifiedUser);
}
