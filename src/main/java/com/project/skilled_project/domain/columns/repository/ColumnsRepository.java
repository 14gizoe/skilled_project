package com.project.skilled_project.domain.columns.repository;

import com.project.skilled_project.domain.columns.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Columns.class, idClass = Long.class)
public interface ColumnsRepository extends JpaRepository<Columns, Long>, ColumnsRepositoryCustom {


}
