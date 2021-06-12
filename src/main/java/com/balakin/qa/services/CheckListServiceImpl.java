package com.balakin.qa.services;


import com.balakin.qa.domain.CheckList;
import com.balakin.qa.domain.Project;
import com.balakin.qa.domain.Row;
import com.balakin.qa.repositories.CheckListRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CheckListServiceImpl implements CheckListService{

    private final CheckListRepository checkListRepository;
    private final RowService rowService;

    public CheckListServiceImpl(CheckListRepository checkListRepository, RowService rowService) {
        this.checkListRepository = checkListRepository;
        this.rowService = rowService;
    }

    @Override
    public List<CheckList> getAllCheckLists() {
        return checkListRepository.findAll();
    }

    @Override
    public CheckList findByProject(Project project) {
        return checkListRepository.findByProject(project);
    }

    @Override
    public CheckList saveOrUpdateCheckList(@RequestBody MultiValueMap<String, String> formData)  {
        CheckList checkList = checkListRepository.findByProject(Project.valueOf(formData.getFirst("project")));
        checkList.setPayload(rowService.getPayload(formData));
        checkListRepository.save(checkList);
        return checkList;
    }

    @Override
    public void deleteById(Long idToDelete) {
        checkListRepository.deleteById(idToDelete);
    }

    @Override
    public List<String> getBlockNames(String payload){
        List<String> nullList = new ArrayList<>();
        if(payload==null){ for (int i = 0; i < 5; i++) {  nullList.add("Введите название оценочного блока");} return nullList;}
        List<String> result = new ArrayList<>();
        String[] pairs = payload.split("\n");
        result = Arrays.stream(pairs).filter( pair->pair.contains("block")).collect(Collectors.toList());
        if(result.size()<5){ for (int i = result.size(); i < 5; i++) {  result.add("Введите название оценочного блока");}}
        System.out.println(result);
        return  result;
    }

    @Override
    public List<String> getCritErrors(String payload){
        List<String> nullList = new ArrayList<>();
        if(payload==null) return nullList;
        List<String> result = new ArrayList<>();
        String[] pairs = payload.split("\n");
        result = Arrays.stream(pairs).filter( pair->pair.contains("error_f")).collect(Collectors.toList());
        return  result;
    }

    @Override
    public List<List<Row>> getBlocks(String payload) throws IOException {
        return rowService.getCheckListBlocks(payload);
    }
}
