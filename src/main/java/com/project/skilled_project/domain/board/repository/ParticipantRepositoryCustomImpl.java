package com.project.skilled_project.domain.board.repository;

import static com.project.skilled_project.domain.board.entity.QParticipant.participant;
import static com.project.skilled_project.domain.user.entity.QUser.user;

import com.project.skilled_project.domain.board.dto.response.UserDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ParticipantRepositoryCustomImpl implements
    ParticipantRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<UserDto> getUsernames(Long boardId) {
    return jpaQueryFactory
        .select(
            Projections.constructor(
                UserDto.class,
                user.username
            )
        )
        .from(user)
        .leftJoin(participant)
        .on(participant.userId.eq(user.id))
        .where(participant.boardId.eq(boardId))
        .fetch();
  }
}
