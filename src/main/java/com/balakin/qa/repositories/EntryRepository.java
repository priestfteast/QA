package com.balakin.qa.repositories;

import com.balakin.qa.domain.Entry;
import com.balakin.qa.domain.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EntryRepository extends JpaRepository<Entry,Long> {

    List<Entry> findByOperatorAndCheckDateBetween(Long OperatorId, LocalDate start, LocalDate end);

}
