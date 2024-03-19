package com.project.skilled_project.domain.checklist.service;

import com.project.skilled_project.domain.checklist.dto.ChecklistDto;

public interface ChecklistService {

  void createCheckList(ChecklistDto checkListDto);

  void deleteCheckList(Long checklistId);

  void toggleChecklist(Long checklistId);
}
