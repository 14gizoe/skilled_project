package com.project.skilled_project.domain.board.repository;


import com.project.skilled_project.domain.board.entity.Participant;
import com.project.skilled_project.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

  List<Long> findAllByBoardId(Long boardId);
}
