package com.project.skilled_project.domain.checklist.service;

import com.project.skilled_project.domain.card.repository.CardRepository;
import com.project.skilled_project.domain.checklist.dto.request.ChecklistRequestDto;
import com.project.skilled_project.domain.checklist.entity.Checklist;
import com.project.skilled_project.domain.checklist.repository.ChecklistRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChecklistServiceImpl implements ChecklistService {

  private final ChecklistRepository checklistRepository;
  private final CardRepository cardRepository;

  @Override
  public void createCheckList(ChecklistRequestDto checkListRequestDto) {
    Checklist checklist = new Checklist(checkListRequestDto);
    cardRepository
        .findById(checkListRequestDto.getCardId())
        .orElseThrow(() -> new EntityNotFoundException("Card not found"));
    checklistRepository.save(checklist);
  }

  @Override
  public void deleteCheckList(Long checklistId) {
    checklistRepository.deleteById(checklistId);
  }

  @Override
  public void toggleChecklist(Long checklistId) {
    Checklist checklist = checklistRepository
        .findById(checklistId)
        .orElseThrow(() -> new EntityNotFoundException("Checklist not found"));
    checklist.toggle();
  }
}
