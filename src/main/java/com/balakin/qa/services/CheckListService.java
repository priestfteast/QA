package com.balakin.qa.services;

import com.balakin.qa.domain.CheckList;
import com.balakin.qa.domain.Project;
import com.balakin.qa.domain.Row;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

public interface CheckListService {
    List<CheckList> getAllCheckLists();
    CheckList findByProject(Project project);
    CheckList saveOrUpdateCheckList(@RequestBody MultiValueMap<String, String> formData) throws Exception;
    void deleteById(Long idToDelete);
    List<String> getBlockNames(String payload);
    List<String> getCritErrors(String payload);
    List<List<Row>> getBlocks(String payload) throws IOException;
}
