package com.balakin.qa.services;

import com.balakin.qa.domain.Entry;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.List;

public interface EntryService {
    List<Entry> findByOperatorAndCheckDateBetween(Long operatorId, LocalDate start, LocalDate end);
    Entry findById(Long id);
    Entry saveOrUpdateEntry(MultiValueMap<String, String> form);
}
