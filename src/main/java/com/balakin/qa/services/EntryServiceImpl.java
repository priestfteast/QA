package com.balakin.qa.services;

import com.balakin.qa.domain.Entry;
import com.balakin.qa.domain.Operator;
import com.balakin.qa.domain.Project;
import com.balakin.qa.domain.Row;
import com.balakin.qa.repositories.EntryRepository;
import com.balakin.qa.repositories.OperatorRepository;
import com.balakin.qa.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;
    private final UserRepository userRepository;
    private final OperatorRepository operatorRepository;
    private final RowService rowService;

    public EntryServiceImpl(EntryRepository entryRepository, UserRepository userRepository, OperatorRepository operatorRepository, RowService rowService) {
        this.entryRepository = entryRepository;
        this.userRepository = userRepository;
        this.operatorRepository = operatorRepository;
        this.rowService = rowService;
    }

    @Override
    public List<Entry> findAllByProjectAndCheckDateBetween(Project project, LocalDate start, LocalDate end) {
        return entryRepository.findAllByProjectAndCheckDateBetween(project, start, end);
    }

    @Override
    public List<Entry> findAllByProjectAndOperatorAndCheckDateBetween(Project project, Operator operator, LocalDate start, LocalDate end) {
        return entryRepository.findAllByProjectAndOperatorAndCheckDateBetween(project, operator, start, end);
    }

    @Override
    public Entry findById(Long id) {
        return entryRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
       entryRepository.deleteById(id);
    }

    @Override
    public Entry saveOrUpdateEntry(MultiValueMap<String, String> form) throws IOException {
        Entry entry;
        if(form.getFirst("id")!=null) {
            Optional entryOptional = entryRepository.findById(Long.valueOf(form.getFirst("id")));
            entry = entryOptional.isPresent() ? (Entry) entryOptional.get() : new Entry();
        }
        else
            entry =new Entry();

        entry.setPayload(rowService.getPayload(form));
        entry.setProject(Project.valueOf(form.getFirst("project")));
        entry.setAuditor(userRepository.findByLogin(form.getFirst("auditor")).get());
        entry.setOperator(operatorRepository.findByFullName(form.getFirst("operator")));
        entry.setCheckDate(LocalDate.now());
        entry.setTotalPoints(countPoints(entry.getPayload()));
        String logs = entry.getLogs()==null ? LocalDate.now()+" by "+entry.getAuditor()+" total points = " +entry.getTotalPoints()+"\n" :
                entry.getLogs()+LocalDate.now()+" by "+entry.getAuditor()+" total points = " +entry.getTotalPoints()+"\n";
        entry.setLogs(logs);

        return entryRepository.save(entry);
    }

    public int countPoints(String payload) throws IOException {
        if(!rowService.castToProperties(payload).getProperty("critical_error").equals("Отсутствуют"))
            return 0;
        else
        return Arrays.stream(payload.split("\n")).mapToInt(line -> line.contains("weight") ?
                Integer.parseInt(line.substring(line.lastIndexOf("=")+1)) : 0).sum();
    }

    @Override
    public List<List<Row>> getBlocks(String checkListPayload, String entryPayload) throws IOException {
        return rowService.getEntryBlocks(checkListPayload, entryPayload);
    }
}
