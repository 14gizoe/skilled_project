package com.project.skilled_project.domain.comment.repository;

import com.project.skilled_project.domain.comment.entity.Comment;
import com.project.skilled_project.domain.comment.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryQuery {

  private final JPAQueryFactory query;

  public Comment getComment(Long commentId, Long userId) {
    QComment comment = QComment.comment;
    return query.select(comment).from(comment)
        .where(comment.id.eq(commentId).and(comment.userId.eq(userId)))
        .fetchOne();
  }
}
