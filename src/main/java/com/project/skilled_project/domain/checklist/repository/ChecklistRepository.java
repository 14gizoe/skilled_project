package com.project.skilled_project.domain.checklist.repository;

import com.project.skilled_project.domain.checklist.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

}