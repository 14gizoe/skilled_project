package com.project.skilled_project.domain.board.repository;

import com.project.skilled_project.domain.board.dto.response.UserDto;
import java.util.List;

public interface ParticipantRepositoryCustom {
  List<UserDto> getUsernames(Long boardId);
}
