package com.balakin.qa.services;

import com.balakin.qa.commands.OperatorCommand;
import com.balakin.qa.domain.CheckList;
import com.balakin.qa.domain.Project;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CheckListService {
    List<CheckList> getAllCheckLists(String request);
    CheckList findByProject(Project project);
    CheckList saveOrUpdateCheckList(@RequestBody MultiValueMap<String, String> formData) throws Exception;
    void deleteById(Long idToDelete);
}
