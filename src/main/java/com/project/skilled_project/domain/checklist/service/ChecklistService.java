package com.project.skilled_project.domain.checklist.service;

import com.project.skilled_project.domain.checklist.dto.request.ChecklistRequestDto;

public interface ChecklistService {

  void createCheckList(ChecklistRequestDto checkListRequestDto);

  void deleteCheckList(Long checklistId);

  void toggleChecklist(Long checklistId);
}
