package com.balakin.qa.services;


import com.balakin.qa.domain.CheckList;
import com.balakin.qa.domain.Project;
import com.balakin.qa.domain.Row;
import com.balakin.qa.repositories.CheckListRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CheckListServiceImpl implements CheckListService{

    private final CheckListRepository checkListRepository;

    public CheckListServiceImpl(CheckListRepository checkListRepository) {
        this.checkListRepository = checkListRepository;
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
        String payload = formData.toString();
        payload = payload.replaceAll(",","\n").replaceAll("[\\[\\]{}]","");
        checkList.setPayload(payload);
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
        return  result;
    }

    @Override
    public List<List<Row>> getBlocks(String payload) {
        if(payload==null) return new ArrayList<>();
        List<List<Row>> blocks = new ArrayList<>();
        List<String> block1, block2, block3, block4, block5 = new ArrayList<>();
        List<String> result = new ArrayList<>();
        String[] pairs = payload.split("\n");
        block1 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_a")||pair.contains("weight_a")).collect(Collectors.toList());
        block2 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_b")||pair.contains("weight_b")).collect(Collectors.toList());
        block3 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_c")||pair.contains("weight_c")).collect(Collectors.toList());
        block4 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_d")||pair.contains("weight_d")).collect(Collectors.toList());
        block5 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_d")||pair.contains("weight_d")).collect(Collectors.toList());
        blocks.add(uniteBlocks(block1));blocks.add(uniteBlocks(block2));blocks.add(uniteBlocks(block3));blocks.add(uniteBlocks(block4)); blocks.add(uniteBlocks(block5));
        return blocks;
    }

    @Override
    public List<Row> uniteBlocks(List<String> blocks) {
        List<Row> result = new ArrayList<>();
        for (int i = 0; i < blocks.size()-1; i++) {
            String row = blocks.get(i)+"%"+blocks.get(i+1);
            result.add(new Row(row));
            i++;
        }
        return result;
    }
}
