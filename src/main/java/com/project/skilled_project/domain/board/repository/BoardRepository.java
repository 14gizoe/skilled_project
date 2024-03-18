package com.project.skilled_project.domain.board.repository;


import com.project.skilled_project.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Board.class, idClass = Long.class)
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

}
