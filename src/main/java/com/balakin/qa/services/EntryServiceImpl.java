package com.balakin.qa.services;

import com.balakin.qa.domain.Entry;
import com.balakin.qa.repositories.EntryRepository;
import com.balakin.qa.repositories.OperatorRepository;
import com.balakin.qa.repositories.UserRepository;
import com.sun.xml.bind.v2.TODO;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EntryServiceImpl implements EntryService{

    private final EntryRepository entryRepository;
    private final UserRepository userRepository;
    private final OperatorRepository operatorRepository;

    public EntryServiceImpl(EntryRepository entryRepository, UserRepository userRepository, OperatorRepository operatorRepository) {
        this.entryRepository = entryRepository;
        this.userRepository = userRepository;
        this.operatorRepository = operatorRepository;
    }

    @Override
    public List<Entry> findByOperatorAndCheckDateBetween(Long operatorId, LocalDate start, LocalDate end) {
        return entryRepository.findByOperatorAndCheckDateBetween(operatorId, start, end);
    }

    @Override
    public Entry findById(Long id) {
        return entryRepository.findById(id).get();
    }

    @Override
    public Entry saveOrUpdateEntry(MultiValueMap<String, String> form) {
        Optional entryOptional = entryRepository.findById(Long.valueOf(form.getFirst("id")));
        Entry entry = entryOptional.isPresent()? (Entry) entryOptional.get() : new Entry();
        entry.setAuditor(userRepository.findByLogin(form.getFirst("auditor")).get());
        entry.setOperator(operatorRepository.findByFullName(form.getFirst("operator")));
        entry.setPayload(form.toString());
        entry.setCheckDate(LocalDate.now());
        entry.setTotalPoints(countPoints(form.toString()));
        entry.setLogs("Commit at "+LocalDate.now()+" by "+entry.getAuditor()+" total points = " +entry.getTotalPoints()+"\n");

        return entryRepository.save(entry);
    }

    public int countPoints(String payload){
        //TODO write counting logic
        return 0;
    }
}
