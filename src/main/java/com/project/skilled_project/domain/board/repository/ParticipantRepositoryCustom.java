package com.project.skilled_project.domain.board.repository;

import java.util.List;

public interface ParticipantRepositoryCustom {

  List<String> getUsernames(Long boardId);
}
