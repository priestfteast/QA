package com.balakin.qa.repositories;

import com.balakin.qa.domain.Entry;
import com.balakin.qa.domain.Operator;
import com.balakin.qa.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EntryRepository extends JpaRepository<Entry,Long> {
    List<Entry> findAllByProjectAndCheckDateBetween(Project project,LocalDate start, LocalDate end);
    List<Entry> findAllByProjectAndOperatorAndCheckDateBetween(Project project, Operator operator, LocalDate start, LocalDate end);
}
