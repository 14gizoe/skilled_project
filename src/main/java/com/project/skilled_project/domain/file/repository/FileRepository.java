package com.project.skilled_project.domain.file.repository;

import com.project.skilled_project.domain.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

}
