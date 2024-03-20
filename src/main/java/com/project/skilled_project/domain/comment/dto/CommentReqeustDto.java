package com.project.skilled_project.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentReqeustDto {

  private String content;

  private Long cardId;
}
