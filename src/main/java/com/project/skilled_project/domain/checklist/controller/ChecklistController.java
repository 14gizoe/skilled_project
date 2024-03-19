package com.project.skilled_project.domain.checklist.controller;

import com.project.skilled_project.domain.checklist.dto.ChecklistDto;
import com.project.skilled_project.domain.checklist.service.ChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/checklist")
@RequiredArgsConstructor
public class ChecklistController {

  private final ChecklistService checklistService;

  @PostMapping
  public void createChecklist(@RequestBody ChecklistDto checkListDto) {
    checklistService.createCheckList(checkListDto);
  }

  @DeleteMapping("/{checklistId}")
  public void deleteChecklist(@PathVariable Long checklistId) {
    checklistService.deleteCheckList(checklistId);
  }

  @PatchMapping("/{checklistId}")
  public void toggleChecklist(@PathVariable Long checklistId) {
    checklistService.toggleChecklist(checklistId);
  }
}
