package com.project.skilled_project.domain.card.repository;

import com.project.skilled_project.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CardRepository extends JpaRepository<Card, Long> {

}
