package com.project.skilled_project.domain.comment.dto.response;


import lombok.Getter;

@Getter
public class CommentResponseDto {
  private Long commentId;
  private String username;
  private String content;

  public CommentResponseDto(Long commentId, String username, String content) {
    this.commentId = commentId;
    this.username = username;
    this.content = content;
  }
}
