package com.project.skilled_project.domain.comment.controller;

import com.project.skilled_project.domain.comment.dto.CommentReqeustDto;
import com.project.skilled_project.domain.comment.dto.CommentUpdateRequestDto;
import com.project.skilled_project.domain.comment.service.CommentServiceImpl;
import com.project.skilled_project.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

  private final CommentServiceImpl commentService;

  @PostMapping
  public ResponseEntity<CommonResponse<Void>> createComment(
      @RequestBody CommentReqeustDto commentRequest,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    commentService.createComment(commentRequest.getContent(), commentRequest.getCardId(),
        userDetails.getUsername());
    return CommonResponse.ok(null);
  }

  @PutMapping("/{commentId}")
  public ResponseEntity<CommonResponse<Void>> updateComment(
    @PathVariable Long commentId,
    @RequestBody CommentUpdateRequestDto commentRequest,
    @AuthenticationPrincipal UserDetails userDetails
  ) {
    commentService.updateComment(commentId, commentRequest.getContent(), userDetails.getUsername());
    return CommonResponse.ok(null);
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<CommonResponse<Void>> deleteComment(
      @PathVariable Long commentId,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    commentService.deleteComment(commentId, userDetails.getUsername());
    return CommonResponse.ok(null);
  }
}
