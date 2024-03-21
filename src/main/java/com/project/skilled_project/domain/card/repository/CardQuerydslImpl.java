package com.project.skilled_project.domain.card.repository;

import static com.project.skilled_project.domain.card.entity.QCard.card;

import com.project.skilled_project.domain.card.dto.response.CardDetailsResponseDto;
import com.project.skilled_project.domain.card.dto.response.CardResponseDto;
import com.project.skilled_project.domain.card.entity.Card;
import com.project.skilled_project.domain.card.entity.QCard;
import com.project.skilled_project.domain.checklist.dto.response.ChecklistResponseDto;
import com.project.skilled_project.domain.checklist.entity.QChecklist;
import com.project.skilled_project.domain.comment.dto.response.CommentResponseDto;
import com.project.skilled_project.domain.comment.entity.QComment;
import com.project.skilled_project.domain.user.entity.QUser;
import com.project.skilled_project.domain.worker.entity.QWorker;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CardQuerydslImpl implements CardQuerydsl {

  private final JPAQueryFactory jpaQueryFactory;
  @Override
  public CardDetailsResponseDto getQueryCard(Long cardId) {
    Card card = jpaQueryFactory.select(QCard.card)
        .from(QCard.card)
        .where(QCard.card.id.eq(cardId))
        .fetchOne();

    List<String> workers = jpaQueryFactory.select(QUser.user.username)
        .from(QCard.card)
        .join(QWorker.worker)
        .on(QCard.card.id.eq(QWorker.worker.cardId))
        .join(QUser.user)
        .on(QUser.user.id.eq(QWorker.worker.userId))
        .where(QWorker.worker.cardId.eq(cardId))
        .fetch();

    List<ChecklistResponseDto> checklists = jpaQueryFactory
        .select(Projections.constructor(ChecklistResponseDto.class,
            QChecklist.checklist.id, QChecklist.checklist.content, QChecklist.checklist.completed))
        .from(QChecklist.checklist)
        .join(QCard.card)
        .on(QCard.card.id.eq(QChecklist.checklist.cardId))
        .where(QChecklist.checklist.cardId.eq(cardId))
        .fetch();

    List<CommentResponseDto> comments = jpaQueryFactory
        .select(Projections.constructor(CommentResponseDto.class,
            QComment.comment.id,  QUser.user.username, QComment.comment.content))
        .from(QComment.comment)
        .join(QUser.user)
        .on(QComment.comment.userId.eq(QUser.user.id))
        .where(QComment.comment.cardId.eq(cardId))
        .fetch();

    return new CardDetailsResponseDto(card, workers, checklists, comments);
  }

  @Override
  public List<CardResponseDto> getQueryCards() {
    return jpaQueryFactory
        .select(Projections.constructor(CardResponseDto.class, card))
        .from(card)
        .fetch();
  }
}
