package com.balakin.qa.services;


import com.balakin.qa.domain.CheckList;
import com.balakin.qa.domain.Project;
import com.balakin.qa.repositories.CheckListRepository;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class CheckListServiceImpl implements CheckListService{

    private final CheckListRepository checkListRepository;

    public CheckListServiceImpl(CheckListRepository checkListRepository) {
        this.checkListRepository = checkListRepository;
    }

    @Override
    public List<CheckList> getAllCheckLists(String request) {
        return checkListRepository.findAll();
    }

    @Override
    public CheckList findByProject(Project project) {
        return checkListRepository.findByProject(project);
    }

    @Override
    public CheckList saveOrUpdateCheckList(@RequestBody MultiValueMap<String, String> formData) throws Exception {
        CheckList checkList = checkListRepository.findByProject(Project.valueOf(formData.getFirst("project")));
        checkList.setPayload(formPayload(formData));
        checkListRepository.save(checkList);

        return checkList;
    }

    @Override
    public void deleteById(Long idToDelete) {
        checkListRepository.deleteById(idToDelete);
    }

    public String formPayload(@RequestBody MultiValueMap<String, String> formData){
        //TODO: form payload from form data
        return  null;
    }
}
