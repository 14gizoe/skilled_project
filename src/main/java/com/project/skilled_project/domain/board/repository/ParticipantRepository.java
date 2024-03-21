package com.project.skilled_project.domain.board.repository;


import com.project.skilled_project.domain.board.entity.Board;
import com.project.skilled_project.domain.board.entity.Participant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Participant.class, idClass = Long.class)
public interface ParticipantRepository extends JpaRepository<Participant, Long>, ParticipantRepositoryCustom {

  @Query("SELECT p.userId FROM Participant p WHERE p.boardId = :boardId")
  List<Long> findAllByBoardId(Long boardId);

  @Query("SELECT p.userId FROM Participant p WHERE p.boardId = :id")
  List<Long> findByUserId(Long id);

  void deleteByBoardIdAndUserIdIn(Long boardId, List<Long> deletingUserIds);
}
