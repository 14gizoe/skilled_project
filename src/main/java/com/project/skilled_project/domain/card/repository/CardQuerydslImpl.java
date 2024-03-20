package com.project.skilled_project.domain.card.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CardQuerydslImpl implements CardQuerydsl {

  private final JPAQueryFactory jpaQueryFactory;

}
