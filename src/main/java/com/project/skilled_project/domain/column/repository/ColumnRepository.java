package com.project.skilled_project.domain.column.repository;

import com.project.skilled_project.domain.column.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Columns.class, idClass = Long.class)
public interface ColumnRepository extends JpaRepository<Columns, Long>, ColumnRepositoryCustom {

}
