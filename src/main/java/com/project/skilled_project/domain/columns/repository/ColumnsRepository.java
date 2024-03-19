package com.project.skilled_project.domain.columns.repository;

import com.project.skilled_project.domain.columns.entity.Columns;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnsRepository extends JpaRepository<Columns, Long> {

  List<Columns> findAllByBoardId(Long BoardId);


}
