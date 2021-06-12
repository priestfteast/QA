package com.balakin.qa.services;

import com.balakin.qa.domain.Entry;
import com.balakin.qa.domain.Operator;
import com.balakin.qa.domain.Project;
import com.balakin.qa.domain.Row;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface EntryService {
    List<Entry> findAllByProjectAndCheckDateBetween(Project project, LocalDate start, LocalDate end);
    List<Entry> findAllByProjectAndOperatorAndCheckDateBetween(Project project, Operator operator, LocalDate start, LocalDate end);
    Entry findById(Long id);
    Entry saveOrUpdateEntry(MultiValueMap<String, String> form) throws IOException;
    void deleteById(Long id);
    List<List<Row>> getBlocks(String checkListPayload, String entryPayload) throws IOException;
}
